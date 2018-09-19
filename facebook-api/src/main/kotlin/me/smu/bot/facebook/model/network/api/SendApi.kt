package me.smu.bot.facebook.model.network.api

import me.smu.bot.facebook.model.network.FacebookHttpService
import me.smu.bot.facebook.model.network.api.data.SendMessageRequest

private const val FACEBOOK_MESSAGES = "messages"

class SendApi(private val service: FacebookHttpService) {

    suspend fun sendMessage(request: SendMessageRequest) {
        return service.post(FACEBOOK_MESSAGES, request)
    }

}