package com.digicap.blahblah.repository

import com.digicap.blahblah.service.entity.ChannelEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChannelRepository: JpaRepository<ChannelEntity, String> {
}