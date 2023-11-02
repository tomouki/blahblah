package com.digicap.blahblah.service.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "CHAT_INFO")
class ChatMessageEntity(
    @Id
    val chat_id: String,
    val channel_id: String,
    val user_id: String,
    val submitted_time: OffsetDateTime,
    val text: String
) {
}