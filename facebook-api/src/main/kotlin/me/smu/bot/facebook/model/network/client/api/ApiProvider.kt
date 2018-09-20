package me.smu.bot.facebook.model.network.client.api

import com.google.gson.Gson
import me.smu.bot.facebook.model.network.client.FacebookHttpClient

internal class ApiProvider(facebookHttpService: FacebookHttpClient) {
    val sendApi: SendApi by lazy { SendApi(facebookHttpService) }
    val attachmentUploadApi: AttachmentUploadApi by lazy { AttachmentUploadApi(facebookHttpService, Gson()) }
    val broadcastApi: BroadcastApi by lazy { BroadcastApi(facebookHttpService) }
}