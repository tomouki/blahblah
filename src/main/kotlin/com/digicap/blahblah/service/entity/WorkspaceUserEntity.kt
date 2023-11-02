package com.digicap.blahblah.service.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

@Entity
@Table(name = "WORKSPACE_USER_MAP")
@IdClass(WorkspaceUserId::class)
class WorkspaceUserEntity (
    @Id
    @Column(name = "WORKSPACE_ID")
    val workspace_id: String,
    @Id
    @Column(name = "USER_ID")
    val user_id: String
) {

}