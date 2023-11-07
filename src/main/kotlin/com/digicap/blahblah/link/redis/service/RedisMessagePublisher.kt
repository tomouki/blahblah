package com.digicap.blahblah.link.redis.service

import com.digicap.blahblah.generated.types.ChatMessage
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisMessagePublisher(private val redisTemplate: RedisTemplate<Any, Any>) {
    fun publish(message: ChatMessage, topic: String) {
        val topic = ChannelTopic(topic)

        redisTemplate.convertAndSend(topic.topic, message)
    }

}