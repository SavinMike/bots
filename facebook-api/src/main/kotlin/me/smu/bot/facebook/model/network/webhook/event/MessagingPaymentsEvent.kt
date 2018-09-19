package me.smu.bot.facebook.model.network.webhook.event

import me.smu.bot.facebook.model.data.Payment
import me.smu.bot.facebook.model.data.User

data class MessagingPaymentsEvent(override val sender: User,
                                  override val recipient: User,
                                  override val timestamp: Long,
                                  val payment: Payment): WebhookEvent