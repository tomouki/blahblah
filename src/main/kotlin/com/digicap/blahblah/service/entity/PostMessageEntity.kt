package com.digicap.blahblah.service.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "POST_INFO")
class PostMessageEntity(
    @Id
    val post_id: String,
    val channel_id: String,
    val user_id: String,
    val submitted_time: OffsetDateTime,
    val title: String,
    val text: String
) {

}