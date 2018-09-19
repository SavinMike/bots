package me.smu.bot.facebook.model.dispatcher.handlers

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.dispatcher.DispatchEvent

interface Handler {
    val groupIdentifier: String?

    val handlerCallback: Handle

    fun interceptEvent(message: DispatchEvent, facebookBot: FacebookBot): Boolean
}
