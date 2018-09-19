package me.smu.bot.facebook.model.network.webhook.event

import me.smu.bot.facebook.model.data.CheckoutInfo
import me.smu.bot.facebook.model.data.LabelPrice
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


/**
 * @property shipping: List of shipping options shown in the checkout dialog.
 */
data class MessageCheckoutUpdateResponse(val shipping: List<Shipping>)

/**
 * @property optionId: ID of shipping option
 * @property optionTitle: Title of option
 * @property priceList: List of line items that are used to update pricing.
 *
 * @see LabelPrice
 */
data class Shipping(
        val optionId: String,
        val optionTitle: String,
        val priceList: List<LabelPrice>)

