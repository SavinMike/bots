package me.smu.bot.facebook.model.data.response

import java.math.BigDecimal

/**
 * @property shipping: List of shipping options shown in the checkout dialog.
 */
data class MessageCheckoutUpdateResponse(val shipping: List<Shipping>) : ResponseData

/**
 * @property optionId: ID of shipping option
 * @property optionTitle: Title of option
 * @property priceList: List of line items that are used to update pricing.
 */
data class Shipping(
        val optionId: String,
        val optionTitle: String,
        val priceList: List<ShippingPrice>)

/**
 * @property label: Label of line item.
 * @property amount: Amount of line item.
 */
data class ShippingPrice(val label: String,
                         val amount: BigDecimal)

