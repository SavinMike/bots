package me.smu.bot.facebook.model.network.client.api.data

import com.google.gson.annotations.SerializedName
import me.smu.bot.facebook.model.data.Attachment
import me.smu.bot.facebook.model.data.QuickReply
import me.smu.bot.facebook.model.network.client.api.data.base.FacebookResponse

/**
 * @property messageType: The messaging type of the message being sent. For supported types and more information, see
 *                        [Sending Messages - Messaging Types](https://developers.facebook.com/docs/messenger-platform/send-messages/#messaging_types)
 * @property recipient: [Recipient] object. Description of the message recipient. All requests must include one of [Recipient.id], [Recipient.phoneNumber], or [Recipient.userRef].
 * @property message: [SendingMessage] object. Cannot be sent with [senderAction]
 * @property senderAction: Message state to display to the user. When using [senderAction], recipient should be the only
 *                         other property set in the request.
 * @property notificationType: Push notification type. Defaults to [NotificationType.REGULAR]
 * @property tag: The message tag string. See [Message Tags](https://developers.facebook.com/docs/messenger-platform/send-messages/message-tags).
 *
 * @see Recipient
 * @see SendingMessage
 */
data class SendMessageRequest(val messageType: MessageType? = null,
                              val recipient: Recipient,
                              val message: SendingMessage? = null,
                              val senderAction: SenderAction? = null,
                              val notificationType: NotificationType? = null,
                              val tag: String? = null)

/**
 * the Send API no longer includes [recipientId] in the response for message sends that use [Recipient.userRef] or [Recipient.phoneNumber]
 * to identify the message recipient.
 *
 * @property recipientId: Unique ID for the user
 * @property messageId: Unique ID for the message
 *
 * @see Recipient
 */
data class SendMessageResponse(val recipientId: String?,
                               val messageId: String) : FacebookResponse


enum class SenderAction {
    /**
     * display the typing bubble
     */
    typing_on,
    /**
     * remove the typing bubble
     */
    typing_off,
    /**
     * display the confirmation icon
     */
    mark_seen
}

enum class NotificationType {
    /**
     * sound/vibration
     */
    REGULAR,
    /**
     * on-screen notification only
     */
    SILENT_PUSH,
    /**
     * no notification
     */
    NO_PUSH
}

/**
 * @property text: Message text. Previews will not be shown for the URLs in this field. Use [attachment] instead.
 * Must be UTF-8 and has a 2000 character limit. [text] or [attachment] must be set.
 * @property attachment: attachment object. Previews the URL. Used to send messages with media or Structured Messages.
 * @property quickReplies: An array of objects the describe the quick reply buttons to send. A maximum of 11 quick
 *                         replies are supported.
 * @property metadata: Custom string that is delivered as a message echo. 1000 character limit.
 */
data class SendingMessage(val text: String? = null,
                          val attachment: Attachment? = null,
                          val quickReplies: List<QuickReply>? = null,
                          val metadata: String? = null)

/**
 * Description of the message recipient. All requests must include one of [id], [phoneNumber], or [userRef]
 *
 * @property id: PSID
 * @property phoneNumber: Phone number of the recipient with the format +1(212)555-2368.
 *                        Your bot must be approved for Customer Matching to send messages this way.
 * @property userRef: user_ref from the checkbox plugin.
 * @property name: Used only if [phoneNumber] is set. Specifies the person's name.
 *                 Providing a name increases the odds of a successful match.
 */
data class Recipient(val id: String? = null,
                     val phoneNumber: String? = null,
                     val userRef: String? = null,
                     val name: UserName? = null)

data class UserName(val firstName: String,
                    val lastName: String)

enum class MessageType {
    /**
     * Message is in response to a received message. This includes promotional and non-promotional messages sent inside
     * the 24-hour standard messaging window or under the 24+1 policy. For example, use this tag to respond if a person
     * asks for a reservation confirmation or an status update.
     */
    @SerializedName("RESPONSE")
    RESPONSE,

    /**
     * Message is being sent proactively and is not in response to a received message. This includes promotional and
     * non-promotional messages sent inside the the 24-hour standard messaging window or under the 24+1 policy.
     */
    @SerializedName("UPDATE")
    UPDATE,

    /**
     * Message is non-promotional and is being sent outside the 24-hour standard messaging window with a message tag.
     * The message must match the allowed use case for the tag.
     */
    @SerializedName("MESSAGE_TAG")
    MESSAGE_TAG
}