package me.smu.bot.facebook.model.data

/**
 * @property id: ID of shipping address
 * @property country: Shipping address country
 * @property city: Shipping address city
 * @property street1: Shipping address street, first line
 * @property street2: Shipping address street, second line
 * @property state: Shipping address state
 * @property postalCode: Shipping address postal code
 */
data class ShippingAddress(val id: String,
                           val country: String,
                           val city: String,
                           val street1: String,
                           val street2: String,
                           val state: String,
                           val postalCode: String)