package me.smu.bot.facebook.model.network.client.api

import me.smu.bot.facebook.model.network.client.FacebookHttpClient
import me.smu.bot.facebook.model.network.client.api.data.*
import me.smu.bot.facebook.model.network.client.api.data.base.FacebookResponse

private const val MESSAGE_CREATIVE_METHOD = "message_creatives"
private const val BROADCAST_METHOD = "broadcast_messages"

internal class BroadcastApi(private val client: FacebookHttpClient) {

    suspend fun createMessageCreative(request: CreateMessageCreativeRequest): CreateMessageCreativeResponse {
        return client.post(MESSAGE_CREATIVE_METHOD, request)
    }

    suspend fun sendBroadcast(request: SendBroadcastRequest): SendBroadcastResponse {
        return client.post(BROADCAST_METHOD, request)
    }

    suspend fun cancelScheduleBroadcast(broadcastId: String): FacebookResponse {
        return client.post(broadcastId, CancelScheduledBroadcastRequest())
    }
}