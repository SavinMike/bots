package me.smu.bot.facebook.model.network.api

import me.smu.bot.facebook.model.network.FacebookHttpService
import me.smu.bot.facebook.model.network.api.data.SendMessageRequest
import me.smu.bot.facebook.model.network.api.data.response.FacebookResponse

private const val FACEBOOK_MESSAGES = "messages"

class SendApi(private val service: FacebookHttpService) {

    suspend fun sendMessage(request: SendMessageRequest): FacebookResponse {
        return service.post(FACEBOOK_MESSAGES, request)
    }

}