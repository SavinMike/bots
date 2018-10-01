package me.smu.bot.facebook.model.network.client.controller

import me.smu.bot.facebook.model.network.client.api.SendApi
import me.smu.bot.facebook.model.network.client.api.data.*

interface SendController {
    suspend fun sendSenderAction(recipient: Recipient, senderAction: SenderAction): SendMessageResponse
    suspend fun sendMessage(recipient: Recipient, text: String): SendMessageResponse
    suspend fun sendMessage(recipient: Recipient, message: SendingMessage, messageType: MessageType = MessageType.MESSAGE_TAG): SendMessageResponse
}

internal class ApiSendController(private val sendApi: SendApi) : SendController {
    override suspend fun sendSenderAction(recipient: Recipient, senderAction: SenderAction): SendMessageResponse {
        return sendApi.sendMessage(SendMessageRequest(senderAction = senderAction, recipient = recipient))
    }

    override suspend fun sendMessage(recipient: Recipient, text: String): SendMessageResponse {
        return sendApi.sendMessage(SendMessageRequest(MessageType.MESSAGE_TAG, recipient, SendingMessage(text = text)))
    }

    override suspend fun sendMessage(recipient: Recipient, message: SendingMessage, messageType: MessageType): SendMessageResponse {
        return sendApi.sendMessage(SendMessageRequest(messageType = messageType, recipient = recipient, message = message))
    }
}