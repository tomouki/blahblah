package com.digicap.blahblah.repository

import com.digicap.blahblah.service.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

//interface ChatRepository: CoroutineCrudRepository<ChatMessageEntity, String> {
@Repository
interface ChatRepository: JpaRepository<ChatMessageEntity, String> {

    @Query(
        nativeQuery = true,
        value =
         """
            SELECT * FROM CHAT_INFO ORDER BY SUBMITTED_TIME DESC LIMIT 10
         """
    )
    fun findLatest(@Param("channel_Id") channelId: String): List<ChatMessageEntity>
}