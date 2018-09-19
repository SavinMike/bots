package me.smu.bot.facebook.model.network.client.api

import com.google.gson.Gson
import me.smu.bot.facebook.model.data.UploadPayload
import me.smu.bot.facebook.model.network.client.FacebookHttpClient
import me.smu.bot.facebook.model.network.client.api.data.AttachmentUploadRequest
import me.smu.bot.facebook.model.network.client.api.data.AttachmentUploadResponse

private const val ATTACHMENT_METHOD = "message_attachments"

internal class AttachmentUploadApi(private val facebookHttpService: FacebookHttpClient,
                                   private val gson: Gson) {

    suspend fun messageAttachments(attachmentUploadRequest: AttachmentUploadRequest): AttachmentUploadResponse {
        val payload = attachmentUploadRequest.message.attachment.payload
        if (payload is UploadPayload) {
            if (payload.file != null) {
                return facebookHttpService.multipart(ATTACHMENT_METHOD) {
                    append("message", gson.toJson(attachmentUploadRequest))
                    append("filedata", payload.file.inputStream())
                } ?: throw IllegalStateException("Can't attach file")
            }

            if (payload.url != null) {
                return facebookHttpService.post(ATTACHMENT_METHOD, attachmentUploadRequest)
            }
        }

        throw IllegalArgumentException("Incorrect request! Payload must have file or url")
    }
}