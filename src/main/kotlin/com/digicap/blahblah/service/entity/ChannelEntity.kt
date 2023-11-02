package com.digicap.blahblah.service.entity

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "CHANNEL_INFO ")
class ChannelEntity(
    @Id
    val channel_id: String,
    val channel_name: String,
    val channel_type: String,
    //@Column(insertable=false, updatable=false)
    val workspace_id: String,
    val create_time: OffsetDateTime,

) {
}