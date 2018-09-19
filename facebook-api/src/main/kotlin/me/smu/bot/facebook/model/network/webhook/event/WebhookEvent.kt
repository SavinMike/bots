package me.smu.bot.facebook.model.network.webhook.event

import me.smu.bot.facebook.model.data.User

interface WebhookEvent {
    val sender: User?
    val recipient: User
    val timestamp: Long
}

class UnknownWebhookEvent(override val recipient: User,
                          override val sender: User?,
                          override val timestamp: Long): WebhookEvent