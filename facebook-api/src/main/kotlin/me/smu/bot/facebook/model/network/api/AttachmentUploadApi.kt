package me.smu.bot.facebook.model.network.api

import com.google.gson.Gson
import me.smu.bot.facebook.model.data.UploadPayload
import me.smu.bot.facebook.model.network.FacebookHttpService
import me.smu.bot.facebook.model.network.api.data.AttachmentUploadRequest
import me.smu.bot.facebook.model.network.api.data.AttachmentUploadResponse

private const val ATTACHMENT_METHOD = "message_attachments"

class AttachmentUploadApi(private val facebookHttpService: FacebookHttpService,
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