package me.smu.bot.facebook.model.dispatcher

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.HandleError
import me.smu.bot.facebook.model.MessageHandle
import me.smu.bot.facebook.model.data.callback.WebhookEvent
import me.smu.bot.facebook.model.dispatcher.handlers.MessageDialogHandler
import me.smu.bot.facebook.model.dispatcher.handlers.OtherwiseDialogHandler
import me.smu.bot.facebook.model.network.api.data.PageServiceRequest
import me.smu.bot.model.exception.BotException

typealias DispatchEvent = WebhookEvent

fun UpdateDispatcher.default(body: Handle) {
    addHandler(OtherwiseDialogHandler(body))
}

fun UpdateDispatcher.message(body: MessageHandle) {
    addHandler(MessageDialogHandler(body))
}

class UpdateDispatcher {
    lateinit var facebookBot: FacebookBot

    private var defaultHandler: DialogHandler? = null
    private val commandHandlers = mutableMapOf<String, ArrayList<DialogHandler>>()
    private val errorHandlers = arrayListOf<HandleError>()

    fun addHandler(handler: DialogHandler) {
        val identifier = handler.groupIdentifier
        if (identifier == null) {
            defaultHandler = handler
            return
        }

        val list = commandHandlers[identifier] ?: arrayListOf<DialogHandler>().apply {
            commandHandlers[identifier] = this
        }
        list.add(handler)
    }

    fun removeHandler(handler: DialogHandler) {
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
        for (group in commandHandlers.entries) {
            val result = group.value
                    .filter { it.interceptEvent(dialog, facebookBot) }
                    .firstOrNull { it.handleDialog.invoke(dialog, facebookBot) }
            if (result != null) {
                return true
            }
        }

        return defaultHandler?.handleDialog?.invoke(dialog, facebookBot)
    }

    fun handleError(botException: BotException) {
        errorHandlers.forEach {
            it(facebookBot, botException)
        }
    }
}