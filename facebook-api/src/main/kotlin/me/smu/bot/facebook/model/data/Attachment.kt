@file:Suppress("EnumEntryName")

package me.smu.bot.facebook.model.data

import java.io.File

/**
 * Type of attachment, may be [AttachmentType.image], [AttachmentType.audio], [AttachmentType.video], [AttachmentType.file]
 * or [AttachmentType.template]. For assets, max file size is 25MB.
 *
 * @property type: audio, fallback, file, image, location or video
 * @property title: title of the attachment
 * @property payload: multimedia or location payload
 */
open class Attachment(val type: AttachmentType,
                      val title: String? = null,
                      val url: String? = null,
                      val payload: Payload? = null)

class TemplateAttachment(payload: TemplatePayload) : Attachment(
        type = AttachmentType.template,
        payload = payload)

enum class AttachmentType {
    audio, fallback, file, image, location, video, template
}

sealed class Payload

/**
 * image, audio, video or file payload
 *
 * @property url: URL of the file
 */
data class FilePayload(val url: String? = null,
                       val attachmentId: String? = null) : Payload()

/**
 * location payload
 *
 * @property lat: Latitude
 * @property long: Longitude
 */
data class LocationPayload(val lat: Double,
                           val long: Double) : Payload()

data class UploadPayload(val isReusable: Boolean,
                         val url: String? = null,
                         val file: File? = null) : Payload()

/**
 * @property sharable: Set to true to enable the native share button in Messenger for the template message.
 */
abstract class TemplatePayload(val templateType: TemplateType) : Payload() {
    abstract val sharable: Boolean?
}

enum class TemplateType {
    button, generic, list, media
}

/**
 * @property title: title of the attachment
 * @property url: URL to the attachment
 * @property type: fallback
 */
data class FallbackPayload(
        val title: String,
        val url: String,
        val type: AttachmentType,
        val payload: String?) : Payload()


