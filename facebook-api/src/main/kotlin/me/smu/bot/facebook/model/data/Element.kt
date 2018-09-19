package me.smu.bot.facebook.model.data

/**
 * At least one property must be set in addition to title.
 *
 * @property title: The title to display in the template. 80 character limit.
 * @property subtitle: The subtitle to display in the template. 80 character limit.
 * @property imageUrl: The URL of the image to display in the template.
 * @property defaultAction:  The default action executed when the template is tapped.
 * @property buttons An array of buttons to append to the template. A maximum of 3 buttons per element is supported.
 *
 * @see UrlButton
 * @see Button
 */
data class Element(val title: String,
                   val subtitle: String? = null,
                   val imageUrl: String? = null,
                   val defaultAction: UrlButton? = null,
                   val buttons: List<Button>? = null)

/**
 * @property mediaType: The type of media being sent
 * @property attachmentId: The attachment ID of the image or video. Cannot be used if [url] is set.
 * @property url: The URL of the image. Cannot be used if [attachmentId] is set.
 * @property buttons: An array of [Button] objects to be appended to the template. A maximum of 1 button is supported.
 *
 * @see Button
 */
data class MediaElement(val mediaType: MediaType,
                        val attachmentId: String? = null,
                        val url: String? = null,
                        val buttons: List<Button>? = null)

enum class MediaType {
    image, video
}