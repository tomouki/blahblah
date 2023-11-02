package com.digicap.blahblah.service

import com.digicap.blahblah.generated.types.*
import com.digicap.blahblah.repository.ChatRepository
import com.digicap.blahblah.repository.PostRepository
import com.digicap.blahblah.service.entity.ChatMessageEntity
import com.digicap.blahblah.service.entity.PostMessageEntity
import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class MessageService(
    private val chatRepository: ChatRepository,
    private val postRepository: PostRepository
) {
    val sender: MutableSharedFlow<ChatMessage> = MutableSharedFlow()

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

    suspend fun addChat(message: Flow<ChatMessage>) {
        message
            .onEach { sender.emit(it) }
            .map {
                ChatMessageEntity(
                    chat_id = UUID.randomUUID().toString(),
                    channel_id = it.channelId,
                    user_id = it.userId,
                    text = it.text,
                    submitted_time = it.submittedTime!!
                )
            }
            .let { chatRepository.saveAll(it.toList()) }
    }

    fun stream(): Flow<ChatMessage> {
        return sender
    }

    fun latest(channelId: String): Flow<ChatMessage> {
        return chatRepository.findLatest(channelId)
            .map {
                ChatMessage(
                    id = it.chat_id,
                    channelId = it.channel_id,
                    userId = it.user_id,
                    text = it.text,
                    submittedTime = it.submitted_time
                )
            }
            .asFlow()
    }
}