package me.smu.bot.facebook.model.network.client.controller

import me.smu.bot.facebook.model.network.client.api.SendApi
import me.smu.bot.facebook.model.network.client.api.data.*

interface SendController {
    suspend fun sendMessage(recipient: Recipient, text: String): SendMessageResponse
    suspend fun sendMessage(recipient: Recipient, message: SendingMessage): SendMessageResponse
}

internal class ApiSendController(private val sendApi: SendApi) : SendController {
    override suspend fun sendMessage(recipient: Recipient, text: String): SendMessageResponse {
        return sendApi.sendMessage(SendMessageRequest(MessageType.MESSAGE_TAG, recipient, SendingMessage(text = text)))
    }

    override suspend fun sendMessage(recipient: Recipient, message: SendingMessage): SendMessageResponse {
        return sendApi.sendMessage(SendMessageRequest(recipient = recipient, message = message))
    }
}