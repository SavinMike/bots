package me.smu.bot.facebook.model

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.dispatcher.DispatchEvent
import me.smu.bot.facebook.model.network.webhook.event.MessagesEvent
import me.smu.bot.facebook.model.network.webhook.event.MessagingPostbacksEvent
import me.smu.bot.model.exception.BotException

typealias Handle = suspend (DispatchEvent, FacebookBot) -> Boolean

typealias MessageHandle = suspend (MessagesEvent, FacebookBot) -> Boolean

typealias PayloadHandle = suspend (MessagingPostbacksEvent, FacebookBot) -> Boolean

typealias HandleError = (FacebookBot, BotException) -> Unit