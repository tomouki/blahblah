package com.digicap.blahblah.repository

import com.digicap.blahblah.service.entity.PostMessageEntity
import com.digicap.blahblah.service.entity.WorkspaceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<PostMessageEntity, String> {
    @Query(
        nativeQuery = true,
        value =
        """
             SELECT post_id, channel_id, submitted_time, title, text, user_id FROM POST_INFO WHERE channel_id = :channel_id ORDER BY submitted_time DESC            
         """
    )
    fun findByChannelId(@Param("channel_id") channelId: String): List<PostMessageEntity>
}