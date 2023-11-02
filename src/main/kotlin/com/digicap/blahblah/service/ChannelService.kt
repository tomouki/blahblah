package com.digicap.blahblah.service

import com.digicap.blahblah.generated.types.Channel
import com.digicap.blahblah.generated.types.ChannelInput
import com.digicap.blahblah.generated.types.ChannelType
import com.digicap.blahblah.repository.ChannelRepository
import com.digicap.blahblah.service.entity.ChannelEntity
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class ChannelService(private val channelRepository: ChannelRepository) {

    fun getChannel(channelId: String): Channel? {
        val entity = channelRepository.findById(channelId)
        if (!entity.isEmpty) {
            return Channel(
                id = entity.get().channel_id,
                workspaceId = entity.get().workspace_id,
                channelName = entity.get().channel_name,
                channelType = ChannelType.valueOf(entity.get().channel_type),
                createTime = entity.get().create_time
            )
        }
        return null
    }

    fun addChannel(input: ChannelInput): Channel {
        val entity = ChannelEntity(
            channel_id = UUID.randomUUID().toString(),
            workspace_id = input.workspaceId,
            channel_name = input.channelName,
            channel_type = input.channelType.name,
            create_time = OffsetDateTime.now(),
        )
        channelRepository.save(entity)
        return Channel(
            id = entity.channel_id,
            workspaceId = entity.workspace_id,
            channelName = entity.channel_name,
            channelType = ChannelType.valueOf(entity.channel_type),
            createTime = entity.create_time
        )
    }

    fun removeChannel(channelId: String): Boolean {
        channelRepository.deleteById(channelId)
        return true
    }

}