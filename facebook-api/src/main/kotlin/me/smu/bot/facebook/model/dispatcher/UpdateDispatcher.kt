package me.smu.bot.facebook.model.dispatcher

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.HandleError
import me.smu.bot.facebook.model.dispatcher.handlers.Handler
import me.smu.bot.facebook.model.dispatcher.handlers.MessageHandlerBuilder
import me.smu.bot.facebook.model.dispatcher.handlers.OtherwiseHandler
import me.smu.bot.facebook.model.dispatcher.handlers.PayloadHandlerBuilder
import me.smu.bot.facebook.model.mapper.mapToBotException
import me.smu.bot.facebook.model.network.webhook.event.WebhookEvent
import me.smu.bot.model.exception.BotException

typealias DispatchEvent = WebhookEvent

fun UpdateDispatcher.default(body: Handle) {
    addHandler(OtherwiseHandler(body))
}

fun UpdateDispatcher.message(body: MessageHandlerBuilder.() -> Unit) {
    addHandler(MessageHandlerBuilder().apply(body).build())
}

fun UpdateDispatcher.payloadEvent(body: PayloadHandlerBuilder.() -> Unit) {
    addHandler(PayloadHandlerBuilder().apply(body).build())
}

class UpdateDispatcher {
    lateinit var facebookBot: FacebookBot

    private var defaultHandler: Handler? = null
    private val commandHandlers = mutableMapOf<String, ArrayList<Handler>>()
    private val errorHandlers = arrayListOf<HandleError>()

    fun addHandler(handler: Handler) {
        val identifier = handler.groupIdentifier
        if (identifier == null) {
            defaultHandler = handler
            return
        }

        val list = commandHandlers[identifier] ?: arrayListOf<Handler>().apply {
            commandHandlers[identifier] = this
        }
        list.add(handler)
    }

    fun removeHandler(handler: Handler) {
        val identifier = handler.groupIdentifier
        if (identifier == null) {
            defaultHandler = null
            return
        }
        commandHandlers[identifier]?.remove(handler)
    }

    fun addErrorHandler(errorHandler: HandleError) {
        errorHandlers.add(errorHandler)
    }

    fun removeErrorHandler(errorHandler: HandleError) {
        errorHandlers.remove(errorHandler)
    }

    suspend fun handleDialog(dialog: DispatchEvent): Boolean? {
        return try {
            for (group in commandHandlers.entries) {
                val result = group.value
                        .filter { it.interceptEvent(dialog, facebookBot) }
                        .firstOrNull { it.handlerCallback.invoke(dialog, facebookBot) }
                if (result != null) {
                    return true
                }
            }

            defaultHandler?.handlerCallback?.invoke(dialog, facebookBot)
        } catch (e: Exception) {
            handleError(e.mapToBotException())
            false
        }
    }

    fun handleError(botException: BotException) {
        errorHandlers.forEach {
            it(facebookBot, botException)
        }
    }
}