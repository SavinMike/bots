package me.smu.bot.facebook.model.dispatcher.handlers

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.PayloadHandle
import me.smu.bot.facebook.model.dispatcher.DispatchEvent
import me.smu.bot.facebook.model.network.webhook.event.MessagingPostbacksEvent

internal class PayloadHandler(private val list: List<PayloadHandle>) : Handler {

    override val handlerCallback: Handle = { event, facebook ->
        list.firstOrNull { it(event as MessagingPostbacksEvent, facebook) } != null
    }
    override val groupIdentifier: String? = "Payload"

    override fun interceptEvent(message: DispatchEvent, facebookBot: FacebookBot): Boolean {
        return message is MessagingPostbacksEvent
    }
}

fun PayloadHandlerBuilder.payload(body: suspend (MessagingPostbacksEvent, FacebookBot) -> suspend String.() -> Boolean) {
    this.textBody = body
}

fun PayloadHandlerBuilder.start(body: PayloadHandle) {
    this.startBody = body
}

fun PayloadHandlerBuilder.default(body: PayloadHandle) {
    this.default = body
}

class PayloadHandlerBuilder {

    internal var default: PayloadHandle = { _, _ -> false }

    internal var startBody: PayloadHandle = { _, _ -> false }
    private val start: PayloadHandle = { event, facebookBot -> if (event.postback.payload == facebookBot.getStartedPayload) startBody(event, facebookBot) else false }

    internal var textBody: suspend (MessagingPostbacksEvent, FacebookBot) -> suspend String.() -> Boolean = { _, _ -> { false } }
    private val text: PayloadHandle = { event, facebookBot -> if (event.postback.payload != null) textBody(event, facebookBot)(event.postback.payload) else false }


    fun build(): Handler {
        return PayloadHandler(listOf(start, default, text))
    }
}