package com.digicap.blahblah.service.entity

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class WorkspaceUserId(val workspace_id: String, val user_id: String): Serializable
