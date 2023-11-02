package com.digicap.blahblah.repository

import com.digicap.blahblah.service.entity.WorkspaceUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkspacpeUserRepository : JpaRepository<WorkspaceUserEntity, String> {

}