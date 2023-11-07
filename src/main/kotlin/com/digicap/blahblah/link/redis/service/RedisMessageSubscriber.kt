package com.digicap.blahblah.link.redis.service

import com.digicap.blahblah.generated.types.ChatMessage
import com.digicap.blahblah.service.chatStream
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Service

@Service
class RedisMessageSubscriber: MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {

        val chatMessage = jacksonObjectMapper().registerModules(JavaTimeModule())
                            .readValue(message.body, ChatMessage::class.java)
        chatStream.next(chatMessage)
    }
}