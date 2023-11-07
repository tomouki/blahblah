package com.digicap.blahblah.config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.netflix.graphql.types.subscription.websockets.Message
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class DgsJacksonConfig {
    @Bean
    @Primary
    @Qualifier("dgsObjectMapper")
    fun customDgsObjectMapper(): ObjectMapper {
        return jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .registerModule(kotlinModule())
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .addMixIn(Message.SubscribeMessage.Payload::class.java, PayloadMixIn::class.java)
    }

    @JsonIgnoreProperties("embeddedIFrameElement", "embedUrl", "httpMultipartParams", "operationId")
    abstract class PayloadMixIn
}