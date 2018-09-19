package me.smu.bot.facebook.model.data

/**
 * Quick Replies allow you to get message recipient input by sending buttons in a message. When a quick reply is tapped,
 * the value of the button is sent in the conversation, and the Messenger Platform sends a [me.smu.bot.facebook.model.network.webhook.event.MessagesEvent] to you webhook.
 *
 * @property title: Required if [contentType] is '[QuickReplyType.text]'. The text to display on the quick reply button.
 *                  20 character limit.
 * @property payload: Required if [contentType] is '[QuickReplyType.text]'. Custom data that will be sent back to you
 * via the [me.smu.bot.facebook.model.network.webhook.event.MessagingPostbacksEvent] webhook event. 1000 character limit.
 * May be set to an empty string if [imageUrl] is set.
 * @property imageUrl: URL of image to display on the quick reply button for text quick replies.
 * Image should be a minimum of 24px x 24px. Larger images will be automatically cropped and resized.
 * Required if [title] is an empty string
 */
data class QuickReply(val contentType: QuickReplyType,
                      val title: String? = null,
                      val payload: String? = null,
                      val imageUrl: String? = null)

enum class QuickReplyType {
    /**
     * Sends a text button
     */
    text,
    /**
     * Sends a button to collect the recipient's location
     */
    location,
    /**
     * Sends a button allowing recipient to send the phone number associated with their account.
     */
    user_phone_number,
    /**
     * Sends a button allowing recipient to send the email associated with their account.
     */
    user_email
}