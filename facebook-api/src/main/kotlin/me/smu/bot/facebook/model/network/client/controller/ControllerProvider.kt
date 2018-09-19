package me.smu.bot.facebook.model.network.client.controller

import me.smu.bot.facebook.model.network.client.api.ApiProvider

interface ControllerProvider {
    val sendController: SendController
    val attachmentUploadController: AttachmentUploadController
}

internal class ApiControllerProvider(apiProvider: ApiProvider) : ControllerProvider {
    override val attachmentUploadController: AttachmentUploadController by lazy { ApiAttachmentUploadController(apiProvider.attachmentUploadApi) }

    override val sendController: SendController by lazy { ApiSendController(apiProvider.sendApi) }
}