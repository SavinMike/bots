package me.smu.bot.facebook.model.network.client.controller

import me.smu.bot.facebook.model.data.Attachment
import me.smu.bot.facebook.model.data.AttachmentType
import me.smu.bot.facebook.model.data.UploadPayload
import me.smu.bot.facebook.model.network.client.api.AttachmentUploadApi
import me.smu.bot.facebook.model.network.client.api.data.AttachmentData
import me.smu.bot.facebook.model.network.client.api.data.AttachmentUploadRequest
import me.smu.bot.facebook.model.network.client.api.data.AttachmentUploadResponse
import java.io.File

/**
 * The Attachment Upload API allows you to upload assets that can be sent in messages at a later time.
 * This allows you to avoid the need to upload commonly used files multiple times. The API supports saving assets
 * from a URL and from your local file system.
 */
interface AttachmentUploadController {
    /**
     * @param url: URL of the file to upload. Max file size is 25MB.
     * @param reusable: Set to true to make the saved asset sendable to other message recipients. Defaults to false.
     */
    suspend fun uploadFromUrl(url: String, type: AttachmentType, reusable: Boolean = false): AttachmentUploadResponse

    suspend fun uploadFromFile(file: File, type: AttachmentType, reusable: Boolean = false): AttachmentUploadResponse
}

internal class ApiAttachmentUploadController(private val attachmentUploadApi: AttachmentUploadApi) : AttachmentUploadController {

    override suspend fun uploadFromUrl(url: String, type: AttachmentType, reusable: Boolean): AttachmentUploadResponse {
        return attachmentUploadApi.messageAttachments(
                AttachmentUploadRequest(AttachmentData(Attachment(type, UploadPayload(reusable, url = url))))
        )
    }

    override suspend fun uploadFromFile(file: File, type: AttachmentType, reusable: Boolean): AttachmentUploadResponse {
        return attachmentUploadApi.messageAttachments(
                AttachmentUploadRequest(AttachmentData(Attachment(type, UploadPayload(reusable, file = file))))
        )
    }
}