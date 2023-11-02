package com.digicap.blahblah.repository

import com.digicap.blahblah.service.entity.WorkspaceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface WorkspaceRepository: JpaRepository<WorkspaceEntity, String> {//CoroutineCrudRepository<WorkspaceVo, String> {

    @Query(
        nativeQuery = true,
        value =
         """
             SELECT workspace_id, user_id, workspace_name, create_time FROM WORKSPACE_INFO WHERE user_id = :user_id ORDER BY create_time DESC            
         """
    )
    fun findByUserId(@Param("user_id") userId: String): List<WorkspaceEntity>

    @Query(
        nativeQuery = true,
        value =
         """
            INSERT INTO WORKSPACE_USER_MAP VALUES (:workspace_id, :user_id)
         """
    )
    fun addUser(@Param("workspace_id") workspaceId: String, @Param("user_id") userId: String)
}