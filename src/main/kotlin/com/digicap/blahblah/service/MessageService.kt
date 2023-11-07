package com.digicap.blahblah.service

import com.digicap.blahblah.generated.types.*
import com.digicap.blahblah.link.redis.service.RedisMessagePublisher
import com.digicap.blahblah.repository.ChatRepository
import com.digicap.blahblah.repository.PostRepository
import com.digicap.blahblah.service.entity.ChatMessageEntity
import com.digicap.blahblah.service.entity.PostMessageEntity
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.reactivestreams.Publisher
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import reactor.core.publisher.ConnectableFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.time.OffsetDateTime
import java.util.*

lateinit var chatStream : FluxSink<ChatMessage>
lateinit var chatPublisher: ConnectableFlux<ChatMessage>

@Service
class MessageService(
    private val chatRepository: ChatRepository,
    private val postRepository: PostRepository,
    private val redisMessagePublisher: RedisMessagePublisher
) : InitializingBean {
    val _topicPrefix = "chat."

    fun getPostList(channelId: String): List<Message> {
        var messages: List<Message> = emptyList()
        postRepository.findByChannelId(channelId)
            .forEach {
                messages = listOf(
                    PostMessage(
                        id = it.post_id,
                        channelId = it.channel_id,
                        userId = it.user_id,
                        submittedTime = it.submitted_time,
                        title = it.title,
                        text = it.text
                    )
                )
            }
        return messages
    }

    fun addPost(message: PostInput): Message {
        val entity = PostMessageEntity(
            post_id = UUID.randomUUID().toString(),
            user_id = message.userId,
            channel_id = message.channelId,
            title = message.title,
            text = message.text,
            submitted_time = OffsetDateTime.now()
        )

        postRepository.save(entity)
        return PostMessage (
            id = entity.post_id,
            userId = entity.user_id,
            channelId = entity.channel_id,
            submittedTime = entity.submitted_time,
            title = entity.title,
            text = entity.text
        )
    }

    fun addChat(message: ChatInput): Message {
        val entity = ChatMessageEntity(
            chat_id = UUID.randomUUID().toString(),
            channel_id = message.channelId,
            user_id = message.userId,
            text = message.text,
            submitted_time = OffsetDateTime.now()
        )

        chatRepository.save(entity)
        val message = ChatMessage (
            id = entity.chat_id,
            userId = entity.user_id,
            channelId = entity.channel_id,
            submittedTime = entity.submitted_time,
            text = entity.text
        )
        redisMessagePublisher.publish(message, _topicPrefix+message.channelId)
        return message
    }

    fun getChat(channelId: String): Publisher<ChatMessage> {
        return chatPublisher
            .filter {
                it.channelId == channelId
            }
    }

//    fun latest(channelId: String): Flow<ChatMessage> {
//        return chatRepository.findLatest(channelId)
//            .map {
//                ChatMessage(
//                    id = it.chat_id,
//                    channelId = it.channel_id,
//                    userId = it.user_id,
//                    text = it.text,
//                    submittedTime = it.submitted_time
//                )
//            }
//            .asFlow()
//    }

    override fun afterPropertiesSet() {
        val publisher = Flux.create<ChatMessage> { emitter ->
            chatStream = emitter
        }

        chatPublisher = publisher.publish()
        chatPublisher.connect()
    }
}