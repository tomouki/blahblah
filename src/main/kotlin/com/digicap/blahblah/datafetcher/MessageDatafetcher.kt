package com.digicap.blahblah.datafetcher

import com.digicap.blahblah.generated.types.ChatInput
import com.digicap.blahblah.generated.types.ChatMessage
import com.digicap.blahblah.generated.types.Message
import com.digicap.blahblah.generated.types.PostInput
import com.digicap.blahblah.service.MessageService
import com.netflix.graphql.dgs.*
import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@DgsComponent
class MessageDatafetcher(private val messageService: MessageService) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @DgsQuery
    fun getPosts(channelId: String): List<Message>  {
        return messageService.getPostList(channelId)
    }
    @DgsMutation
    fun addPost(@InputArgument message: PostInput): Message {
        return messageService.addPost(message)
    }

    @DgsMutation
    fun sendChat(@InputArgument message: ChatInput): Message {
        return messageService.addChat(message)
    }

    @DgsSubscription
    fun receiveChat(@InputArgument channelId: String): Publisher<ChatMessage> {
        logger.debug(":::::::::::::::::::::::::::::::::::::::::::::::")
        return messageService.getChat(channelId)
    }
}