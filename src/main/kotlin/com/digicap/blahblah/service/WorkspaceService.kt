package com.digicap.blahblah.service

import com.digicap.blahblah.generated.types.Channel
import com.digicap.blahblah.generated.types.ChannelType
import com.digicap.blahblah.generated.types.Workspace
import com.digicap.blahblah.repository.WorkspaceRepository
import com.digicap.blahblah.repository.WorkspacpeUserRepository
import com.digicap.blahblah.service.entity.WorkspaceEntity
import com.digicap.blahblah.service.entity.WorkspaceUserEntity
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class WorkspaceService(
    private val workspaceRepository: WorkspaceRepository,
    private val workspaceUserRepository: WorkspacpeUserRepository) {

    fun getWorkspaceList(userId: String): List<Workspace> {
        return workspaceRepository.findByUserId(userId)
            .map {
                    var chnnels = ArrayList<Channel>()

                    it.channels?.forEach {
                        chnnels.add (Channel(
                            id = it!!.channel_id,
                            workspaceId = it.workspace_id,
                            channelName = it.channel_name,
                            channelType = ChannelType.valueOf(it.channel_type),
                            createTime = it.create_time
                        ))
                    }
                    Workspace(
                        id = it.workspace_id,
                        workspaceName = it.workspace_name,
                        userId = it.user_id,
                        createTime = it.create_time,
                        channels = chnnels

                    )
            }
    }

    fun addWorkspace(userId: String, workspaceName: String) {
        val workspaceEntity = WorkspaceEntity(workspace_id = UUID.randomUUID().toString(),
            workspace_name = workspaceName,
            user_id = userId,
            create_time = OffsetDateTime.now(),
            channels = emptyList()
        )
        workspaceRepository.save(workspaceEntity)
    }

    fun getWorkspace(workspaceId: String): Workspace? {
        val entity = workspaceRepository.findById(workspaceId)

        if (!entity.isEmpty) {
            var chnnels = ArrayList<Channel>()

            entity.get().channels?.forEach {
                chnnels.add (Channel(
                    id = it!!.channel_id,
                    workspaceId = it.workspace_id,
                    channelName = it.channel_name,
                    channelType = ChannelType.valueOf(it.channel_type),
                    createTime = it.create_time
                ))
            }

            return Workspace(
                id = entity.get().workspace_id,
                workspaceName = entity.get().workspace_name,
                userId = entity.get().user_id,
                createTime = entity.get().create_time,
                channels = chnnels
            )
        }
        return null
    }

    fun addUser(workspaceId: String, userIdList: List<String>): Boolean {
        val entity = userIdList.map {
            WorkspaceUserEntity(
                workspace_id = workspaceId,
                user_id = it
            )
        }
        return workspaceUserRepository.saveAll(entity).isEmpty()
    }

    fun removeWorkspace(workspaceId: String): Boolean {
        workspaceRepository.deleteById(workspaceId)
        return true
    }
}