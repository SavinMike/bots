package me.smu.bot.facebook.model.dispatcher

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle

interface DialogHandler {
    val groupIdentifier: String?

    val handleDialog: Handle

    fun interceptEvent(message: DispatchEvent, facebookBot: FacebookBot): Boolean
}
