package com.digicap.blahblah.datafetcher

import com.digicap.blahblah.generated.types.ChatMessage
import com.digicap.blahblah.service.MessageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.onStart
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RestController


@RestController
@MessageMapping("api.v1.messages")
class ChatResource(private val messageService: MessageService) {

    @MessageMapping("stream")
    suspend fun receive(
        @Payload inboundMessage: Flow<ChatMessage>) {
        messageService.addChat(inboundMessage)
    }

    @MessageMapping("stream/{channelId}")
    fun send(@DestinationVariable channelId: String): Flow<ChatMessage> {
        return messageService
            .stream()
            .onStart { emitAll(messageService.latest(channelId)) }
    }
}