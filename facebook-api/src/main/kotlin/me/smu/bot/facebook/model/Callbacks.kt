package me.smu.bot.facebook.model

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.data.callback.MessagesEvent
import me.smu.bot.facebook.model.dispatcher.DispatchEvent
import me.smu.bot.model.exception.BotException

typealias Handle = suspend (DispatchEvent, FacebookBot) -> Boolean

typealias MessageHandle = suspend (MessagesEvent, FacebookBot) -> Boolean

typealias HandleError = (FacebookBot, BotException) -> Unit