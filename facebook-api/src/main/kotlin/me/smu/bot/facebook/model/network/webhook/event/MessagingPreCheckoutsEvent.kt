package me.smu.bot.facebook.model.network.webhook.event

import me.smu.bot.facebook.model.data.CheckoutInfo
import me.smu.bot.facebook.model.data.User

/**
 * This callback will occur when a user clicks on Pay in the payment dialog, but before the user's card is charged.
 * This allows you to do any processing on your end before charging user's card.
 * You could check inventory levels or for price changes before accepting the payment.
 */
data class MessagingPreCheckoutsEvent(override val sender: User?,
                                      override val recipient: User,
                                      override val timestamp: Long,
                                      val preCheckout: CheckoutInfo): WebhookEvent