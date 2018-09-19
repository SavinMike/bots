package me.smu.bot.facebook.model.dispatcher.handlers

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.MessageHandle
import me.smu.bot.facebook.model.dispatcher.DispatchEvent
import me.smu.bot.facebook.model.network.webhook.event.MessagesEvent

class MessageHandler(private val messageHandle: MessageHandle) : Handler {

    override val handlerCallback: Handle = { event, facebook ->
        messageHandle(event as MessagesEvent, facebook)
    }
    override val groupIdentifier: String? = "Message"

    override fun interceptEvent(message: DispatchEvent, facebookBot: FacebookBot): Boolean {
        return message is MessagesEvent
    }
}