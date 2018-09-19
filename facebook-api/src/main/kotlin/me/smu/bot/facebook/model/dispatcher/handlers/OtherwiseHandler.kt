package me.smu.bot.facebook.model.dispatcher.handlers

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.dispatcher.DispatchEvent

class OtherwiseHandler(override val handlerCallback: Handle) : Handler {
    override val groupIdentifier: String? = null

    override fun interceptEvent(message: DispatchEvent, facebookBot: FacebookBot): Boolean {
        return true
    }
}

