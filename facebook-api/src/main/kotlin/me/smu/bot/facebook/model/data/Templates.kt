package me.smu.bot.facebook.model.data

/**
 * The [ButtonTemplate] allows you to send a structured message that includes text and buttons.
 *
 * @property text: UTF-8-encoded text of up to 640 characters. Text will appear above the buttons.
 * @property buttons: Set of 1-3 buttons that appear as call-to-actions.
 *
 * @see Button
 * @see TemplatePayload
 */
data class ButtonTemplate(
        val text: String,
        val buttons: List<Button>,
        override val sharable: Boolean = false
) : TemplatePayload(TemplateType.button)

/**
 * The [GenericTemplate] allows you to send a structured message that includes an image, text and buttons.
 * A generic template with multiple templates described in the elements array will send a horizontally scrollable carousel
 * of items, each composed of an image, text and buttons.
 *
 * For complete implementation details, see [Generic Template](https://developers.facebook.com/docs/messenger-platform/send-api-reference/generic-template/).
 *
 * @property imageAspectRatio: The aspect ratio used to render images specified by [Element.imageUrl].
 * @property elements: An array of element objects that describe instances of the generic template to be sent.
 *                     Specifying multiple elements will send a horizontally scrollable carousel of templates.
 *                     A maximum of 10 elements is supported.
 */
data class GenericTemplate(
        val elements: List<Element>,
        val imageAspectRatio: AspectRatio? = null,
        override val sharable: Boolean = false
) : TemplatePayload(TemplateType.generic)

enum class AspectRatio {
    /**
     * 1.91:1
     */
    horizontal,

    /**
     * 1:1
     */
    square
}

/**
 * The [ListTemplate] allows you to send a structured message with a set of items rendered vertically.
 *
 * For implementation details, see [List Template](https://developers.facebook.com/docs/messenger-platform/send-api-reference/list-template).
 *
 * @property elements: Array of objects that describe items in the list. Minimum of 2 elements required. Maximum of 4 elements is supported.
 * @property buttons: [Button] to display at the bottom of the list. Maximum of 1 button is supported.
 * @property topElementStyle: Sets the format of the first list items. Messenger web client currently only renders compact.
 *
 * @see Button
 */
data class ListTemplate(
        val elements: List<Element>,
        val buttons: List<Button>,
        val topElementStyle: ElementStyle? = null,
        override val sharable: Boolean? = null
) : TemplatePayload(TemplateType.list)

enum class ElementStyle {
    /**
     * Renders a plain list item.
     */
    compact,
    /**
     * Renders the first list item as a cover item.
     */
    large
}

/**
 * The [MediaTemplate] allows you to send a structured message that includes an image or video, and an optional button.
 *
 * For complete implementation details, see [Media Template](https://developers.facebook.com/docs/messenger-platform/send-api-reference/media-template).
 *
 * @property elements: An array containing 1 element object that describe the media in the message. A maximum of 1 element is supported.
 *
 * @see MediaElement
 */
data class MediaTemplate(
        val elements: List<MediaElement>,
        override val sharable: Boolean?
) : TemplatePayload(TemplateType.media)

//TODO add Open Graph, Receipt and Airplain Templates

