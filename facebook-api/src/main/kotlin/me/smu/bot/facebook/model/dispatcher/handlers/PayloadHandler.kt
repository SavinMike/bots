package me.smu.bot.facebook.model.dispatcher.handlers

import me.smu.bot.facebook.FacebookBot
import me.smu.bot.facebook.model.Handle
import me.smu.bot.facebook.model.PayloadHandle
import me.smu.bot.facebook.model.dispatcher.DispatchEvent
import me.smu.bot.facebook.model.network.webhook.event.MessagingPostbacksEvent
import me.smu.bot.facebook.model.network.webhook.event.WebhookEvent

internal class PayloadHandler(private val list: List<PayloadHandle>) : Handler {

    override val handlerCallback: Handle = { event, facebook ->
        list.firstOrNull { it(event as MessagingPostbacksEvent, facebook) } != null
    }
    override val groupIdentifier: String? = "Payload"

    override fun interceptEvent(message: DispatchEvent, facebookBot: FacebookBot): Boolean {
        return message is MessagingPostbacksEvent
    }
}

fun PayloadHandlerBuilder.start(body: suspend (WebhookEvent, FacebookBot) -> Boolean) {
    this.startBody = body
}

class PayloadHandlerBuilder(var default: PayloadHandle = { _, _ -> false }) {

    internal var startBody: suspend (WebhookEvent, FacebookBot) -> Boolean = { _, _ -> true }
    private val start: PayloadHandle = { event, facebookBot -> if (event.postback.payload == facebookBot.getStartedPayload) startBody(event, facebookBot) else false }

    fun build(): Handler {
        return PayloadHandler(listOf(start, default))
    }
}