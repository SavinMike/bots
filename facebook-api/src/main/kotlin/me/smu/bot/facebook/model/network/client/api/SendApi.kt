package me.smu.bot.facebook.model.network.client.api

import me.smu.bot.facebook.model.network.client.FacebookHttpClient
import me.smu.bot.facebook.model.network.client.api.data.SendMessageRequest
import me.smu.bot.facebook.model.network.client.api.data.SendMessageResponse

private const val FACEBOOK_MESSAGES = "messages"

internal class SendApi(private val service: FacebookHttpClient) {

    suspend fun sendMessage(request: SendMessageRequest): SendMessageResponse {
        return service.post(FACEBOOK_MESSAGES, request)
    }

}