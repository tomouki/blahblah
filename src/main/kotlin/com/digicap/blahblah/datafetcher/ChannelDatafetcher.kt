package com.digicap.blahblah.datafetcher

import com.digicap.blahblah.generated.types.Channel
import com.digicap.blahblah.generated.types.ChannelInput
import com.digicap.blahblah.service.ChannelService
import com.digicap.blahblah.service.WorkspaceService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class ChannelDatafetcher(private val channelService: ChannelService) {

    @DgsQuery
    fun viewChannel(@InputArgument channelId: String): Channel? {
        return channelService.getChannel(channelId)
    }

    @DgsMutation
    fun addChannel(@InputArgument input: ChannelInput): Channel {
        return channelService.addChannel(input)
    }

    @DgsMutation
    fun removeChannel(@InputArgument channelId: String): Boolean {
        return channelService.removeChannel(channelId)
    }
}