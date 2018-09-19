package me.smu.bot.facebook.model.network.api.data

import me.smu.bot.facebook.model.data.Attachment

data class AttachmentUploadRequest(val message: AttachmentData)

/**
 * @property attachmentId: The reusable attachment ID
 */
data class AttachmentUploadResponse(val attachmentId: String)


data class AttachmentData(val attachment: Attachment)