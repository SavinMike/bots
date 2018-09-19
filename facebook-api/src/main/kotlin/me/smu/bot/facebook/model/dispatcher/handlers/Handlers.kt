package me.smu.bot.facebook.model.dispatcher.handlers

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.MessageHandle
import me.smu.bot.facebook.model.data.callback.MessagesEvent
import me.smu.bot.facebook.model.dispatcher.DialogHandler
import me.smu.bot.facebook.model.dispatcher.DispatchEvent

class OtherwiseDialogHandler(override val handleDialog: Handle) : DialogHandler {
    override val groupIdentifier: String? = null

    override fun interceptEvent(message: DispatchEvent, facebookBot: FacebookBot): Boolean {
        return true
    }
}

class MessageDialogHandler(private val messageHandle: MessageHandle) : DialogHandler {

    override val handleDialog: Handle = { event, facebook ->
        messageHandle(event as MessagesEvent, facebook)
    }
    override val groupIdentifier: String? = "Message"

    override fun interceptEvent(message: DispatchEvent, facebookBot: FacebookBot): Boolean {
        return message is MessagesEvent
    }
}
