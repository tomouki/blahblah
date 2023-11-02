package com.digicap.blahblah.datafetcher

import com.digicap.blahblah.generated.types.Workspace
import com.digicap.blahblah.service.WorkspaceService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@DgsComponent
class WorkspaceDatafetcher(private val workspaceService: WorkspaceService) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @DgsQuery
    fun getWorkspaces(@InputArgument userId: String): List<Workspace> {
        return workspaceService.getWorkspaceList(userId)
    }

    @DgsQuery
    fun viewWorkspace(@InputArgument workspaceId: String): Workspace? {
        return workspaceService.getWorkspace(workspaceId)
    }

    @DgsMutation
    fun addWorkspace(@InputArgument userId: String, @InputArgument workspaceName: String) {
        return workspaceService.addWorkspace(userId, workspaceName)
    }

    @DgsMutation
    fun joinWorkspace(@InputArgument workspaceId: String, @InputArgument userIdList: List<String>): Boolean {
        return workspaceService.addUser(workspaceId, userIdList)
    }

    @DgsMutation
    fun removeWorkspace(@InputArgument workspaceId: String): Boolean {
        return workspaceService.removeWorkspace(workspaceId)
    }
}