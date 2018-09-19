package me.smu.bot.facebook.model.dispatcher.handlers

import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.dispatcher.DispatchEvent

abstract class Handler(val handlerCallback: Handle) {
    abstract val groupIdentifier: String

    abstract fun checkUpdate(update: DispatchEvent): Boolean
}