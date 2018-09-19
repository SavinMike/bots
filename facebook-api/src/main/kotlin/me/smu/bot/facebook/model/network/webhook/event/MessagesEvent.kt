package me.smu.bot.facebook.model.network.webhook.event

import me.smu.bot.facebook.model.data.Attachment
import me.smu.bot.facebook.model.data.QuickReply
import me.smu.bot.facebook.model.data.User

/**
 * This callback will occur when a message has been sent to your page.
 * You may receive text messages or messages with attachments (image, audio, video, file, or location).
 * You may also receive fallback attachments, which are attachments in Messenger other than the ones mentioned above.
 * A common example is attachments created from link scraping. Messages are always sent in order.
 *
 * @property sender: PSID
 * @property recipient: Page Id
 * @property timestamp: time when event happened
 *
 * @see Message
 * @see User
 */
data class MessagesEvent(override val sender: User,
                         override val recipient: User,
                         override val timestamp: Long,
                         val message: Message?) : WebhookEvent

/**
 * @property mid: Message ID
 * @property text: Text of message
 * @property attachments: Array containing attachment data
 * @property quickReply: Optional custom data provided by the sending app
 *
 * @see Attachment
 * @see QuickReply
 */
data class Message(val mid: String? = null,
                   val text: String? = null,
                   val attachments: List<Attachment>? = null,
                   val quickReply: QuickReply? = null)