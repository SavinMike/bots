package me.smu.bot.facebook.model.data

/**
 * @property payload: Metadata defined in the Buy Button.
 * @property shippingAddress: The person's shipping address.
 */
data class CheckoutInfo(val payload: String,
                        val shippingAddress: ShippingAddress)