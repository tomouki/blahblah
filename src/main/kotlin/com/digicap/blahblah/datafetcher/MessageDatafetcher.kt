package com.digicap.blahblah.datafetcher

import com.digicap.blahblah.generated.types.ChannelType
import com.digicap.blahblah.generated.types.ChatInput
import com.digicap.blahblah.generated.types.Message
import com.digicap.blahblah.generated.types.PostInput
import com.digicap.blahblah.service.MessageService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import kotlinx.coroutines.flow.toList

@DgsComponent
class MessageDatafetcher(private val messageService: MessageService) {

    @DgsQuery
    fun getPosts(channelId: String): List<Message>  {
        return messageService.getPostList(channelId)
    }

    @DgsMutation
    fun addPost(message: PostInput): Message {
        return messageService.addPost(message)
    }
}