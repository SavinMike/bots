package me.smu.bot.facebook.model.network.controller.attachment

import me.smu.bot.facebook.model.data.Attachment
import me.smu.bot.facebook.model.data.AttachmentType
import me.smu.bot.facebook.model.data.UploadPayload
import me.smu.bot.facebook.model.network.api.AttachmentUploadApi
import me.smu.bot.facebook.model.network.api.data.AttachmentData
import me.smu.bot.facebook.model.network.api.data.AttachmentUploadRequest
import me.smu.bot.facebook.model.network.api.data.AttachmentUploadResponse
import java.io.File

class AttachmentUploadController(private val attachmentUploadApi: AttachmentUploadApi) {

    /**
     * @param url: URL of the file to upload. Max file size is 25MB.
     * @param reusable: Set to true to make the saved asset sendable to other message recipients. Defaults to false.
     */
    suspend fun uploadFromUrl(url: String, type: AttachmentType, reusable: Boolean = false): AttachmentUploadResponse {
        return attachmentUploadApi.messageAttachments(
                AttachmentUploadRequest(AttachmentData(Attachment(type, UploadPayload(reusable, url = url))))
        )
    }

    suspend fun uploadFromFile(file: File, type: AttachmentType, reusable: Boolean = false): AttachmentUploadResponse {
        return attachmentUploadApi.messageAttachments(
                AttachmentUploadRequest(AttachmentData(Attachment(type, UploadPayload(reusable, file = file))))
        )
    }
}