package com.digicap.blahblah.service.entity

import com.digicap.blahblah.generated.types.Channel
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "WORKSPACE_INFO")
class WorkspaceEntity(
    @Id
    val workspace_id: String,
    val user_id: String,
    val workspace_name: String,
    val create_time: OffsetDateTime,

    //@OneToMany(mappedBy="workspace")
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "workspace_id")
    val channels: List<ChannelEntity?>?
) {
}