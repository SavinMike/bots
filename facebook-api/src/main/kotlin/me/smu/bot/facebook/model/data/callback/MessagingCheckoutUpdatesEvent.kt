package me.smu.bot.facebook.model.data.callback

import me.smu.bot.facebook.model.data.CheckoutInfo
import me.smu.bot.facebook.model.data.User

/**
 * This callback enables you to update pricing for flexible-amount transactions on the checkout dialog displayed by the Buy Button.
 * After the Buy Button is tapped, a call is made to the webhook containing the person's shipping address.
 * This enables you to update pricing with shipping and taxes based on a person's location.
 * This callback is made each time the shipping address is changed.
 */
data class MessagingCheckoutUpdatesEvent(override val sender: User,
                                         override val recipient: User,
                                         override val timestamp: Long,
                                         val checkoutUpdate: CheckoutInfo) : WebhookEvent