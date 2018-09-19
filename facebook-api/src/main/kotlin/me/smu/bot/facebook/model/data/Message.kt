package me.smu.bot.facebook.model.data

/**
 * @property mid: Message ID
 * @property text: Text of message
 * @property attachments: Array containing attachment data
 * @property quickReply: Optional custom data provided by the sending app
 */
data class Message(val mid: String? = null,
                   val text: String?,
                   val attachments: List<Attachment>? = null,
                   val quickReply: Any? = null)