package me.smu.bot.facebook.model.dispatcher.handlers

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.MessageHandle
import me.smu.bot.facebook.model.data.Attachment
import me.smu.bot.facebook.model.data.QuickReply
import me.smu.bot.facebook.model.dispatcher.DispatchEvent
import me.smu.bot.facebook.model.network.webhook.event.MessagesEvent

internal class MessageHandler(private val list: List<MessageHandle>) : Handler {

    override val handlerCallback: Handle = { event, facebook ->
        list.firstOrNull { it(event as MessagesEvent, facebook) } != null
    }
    override val groupIdentifier: String? = "Payload"

    override fun interceptEvent(message: DispatchEvent, facebookBot: FacebookBot): Boolean {
        return message is MessagesEvent
    }
}

fun MessageHandlerBuilder.text(body: (MessagesEvent, FacebookBot) -> suspend String.() -> Boolean) {
    this.text = body
}

fun MessageHandlerBuilder.default(body: MessageHandle) {
    this.default = body
}

fun MessageHandlerBuilder.reply(body: (MessagesEvent, FacebookBot) -> suspend QuickReply.() -> Boolean) {
    this.reply = body
}

fun MessageHandlerBuilder.attachment(body: (MessagesEvent, FacebookBot) -> suspend Attachment.() -> Boolean) {
    this.attachment = body
}

class MessageHandlerBuilder {
    var reply: (MessagesEvent, FacebookBot) -> suspend QuickReply.() -> Boolean = { _, _ -> { false } }
    var attachment: (MessagesEvent, FacebookBot) -> suspend Attachment.() -> Boolean = { _, _ -> { false } }
    var text: (MessagesEvent, FacebookBot) -> suspend String.() -> Boolean = { _, _ -> { false } }

    var default: MessageHandle = { _, _ -> false }

    fun build(): Handler {
        return MessageHandler(listOf({ event, facebook ->
            var result = event.message?.quickReply?.let {
                reply(event, facebook)(it)
            }

            if (result != true) {
                result = event.message?.attachments?.firstOrNull()?.let {
                    attachment(event, facebook)(it)
                }
            }

            if (result != true) {
                result = event.message?.text?.let {
                    text(event, facebook)(it)
                }
            }

            result ?: default(event, facebook)
        }))
    }
}
