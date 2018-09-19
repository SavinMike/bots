package me.smu.bot.facebook.model.dispatcher.handlers

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.dispatcher.DialogHandler
import me.smu.bot.facebook.model.dispatcher.DispatchEvent

class TextHandler(private val text: String? = null, override val handleDialog: Handle) : DialogHandler {
    override val groupIdentifier: String = "CommandHandler"

    override fun interceptEvent(message: DispatchEvent, facebookBot: FacebookBot): Boolean {
        return false
    }
}