package me.smu.bot.facebook.model.data

import com.google.gson.annotations.SerializedName
import java.io.File

/**
 * @property type: audio, fallback, file, image, location or video
 * @property payload: multimedia or location payload
 */
data class Attachment(val type: AttachmentType,
                      val payload: UploadPayload)

enum class AttachmentType {
    @SerializedName("audio")
    AUDIO,
    @SerializedName("fallback")
    FALLBACK,
    @SerializedName("file")
    FILE,
    @SerializedName("image")
    IMAGE,
    @SerializedName("LOCATION")
    LOCATION,
    @SerializedName("VIDEO")
    VIDEO
}

sealed class Payload

/**
 * image, audio, video or file payload
 *
 * @property url: URL of the file
 */
data class FilePayload(val url: String) : Payload()

/**
 * location payload
 *
 * @property lat: Latitude
 * @property lng: Longitude
 */
data class LocationPayload(val lat: Double,
                           val lng: Double) : Payload()

data class UploadPayload(val isReusable: Boolean,
                         val url: String? = null,
                         val file: File? = null): Payload()

/**
 * @property title: title of the attachment
 * @property url: URL to the attachment
 * @property type: fallback
 */
data class Fallback(
        val title: String,
        val url: String,
        val type: AttachmentType)