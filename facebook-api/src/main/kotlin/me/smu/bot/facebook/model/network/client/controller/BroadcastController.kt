package me.smu.bot.facebook.model.network.client.controller

import me.smu.bot.facebook.model.network.client.api.BroadcastApi
import me.smu.bot.facebook.model.network.client.api.data.*
import me.smu.bot.facebook.model.network.client.api.data.base.FacebookResponse

/**
 * The Messenger Platform's Broadcast API allows you to broadcast a message to everyone that currently has an open conversation
 * with your Page or a custom set of people.
 *
 * To use the broadcast API, your Messenger bot must have the following permissions:
 * 1. pages_messaging
 * 2. subscription messaging
 *
 * For more information on using the Broadcast API, see [Broadcasting Messages to Multiple Conversations](https://developers.facebook.com/docs/messenger-platform/send-messages/broadcast-messages/).
 *
 * Delivery receipts, read receipts and echo webhook events will not be returned for broadcast messages.
 *
 * @since v2.11
 */
interface BroadcastController {

    /**
     * To create a broadcast message, where [message] is any message you would normally send via the [SendController].
     * Only one message may be defined per Message Creative.
     *
     * @return On success, the Broadcast API will return an object with a message_creative_id property that can be used to send the message
     */
    suspend fun createMessageCreative(message: SendingMessage): CreateMessageCreativeResponse

    /**
     * To send a broadcast message, include the broadcast message ID in the [SendBroadcastRequest.messageCreativeId]
     * property of the body of the request.
     *
     * By default, broadcast messages are sent to all open conversations.
     */
    suspend fun sendBroadcastMessage(request: SendBroadcastRequest): SendBroadcastResponse

    suspend fun cancelScheduleBroadcastMessage(broadcastId: String): FacebookResponse
}

internal class ClientBroadcastController(private val broadcastApi: BroadcastApi) : BroadcastController {
    override suspend fun createMessageCreative(message: SendingMessage): CreateMessageCreativeResponse {
        return broadcastApi.createMessageCreative(CreateMessageCreativeRequest(listOf(message)))
    }

    override suspend fun sendBroadcastMessage(request: SendBroadcastRequest): SendBroadcastResponse {
        return broadcastApi.sendBroadcast(request)
    }

    override suspend fun cancelScheduleBroadcastMessage(broadcastId: String): FacebookResponse {
        return broadcastApi.cancelScheduleBroadcast(broadcastId)
    }
}