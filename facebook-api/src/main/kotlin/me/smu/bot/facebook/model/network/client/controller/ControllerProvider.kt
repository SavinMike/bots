package me.smu.bot.facebook.model.network.client.controller

import me.smu.bot.facebook.model.network.client.api.ApiProvider

interface ControllerProvider {
    val sendController: SendController
    val attachmentUploadController: AttachmentUploadController
    val broadcastController: BroadcastController
}

internal class ApiControllerProvider(apiProvider: ApiProvider) : ControllerProvider {
    override val attachmentUploadController: AttachmentUploadController by lazy { ApiAttachmentUploadController(apiProvider.attachmentUploadApi) }

    override val sendController: SendController by lazy { ApiSendController(apiProvider.sendApi) }

    override val broadcastController: BroadcastController by lazy { ClientBroadcastController(apiProvider.broadcastApi) }
}