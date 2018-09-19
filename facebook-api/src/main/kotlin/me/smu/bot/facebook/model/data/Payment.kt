package me.smu.bot.facebook.model.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * @param payload: Metadata defined in the Buy Button.
 * @param requestedUserInfo: Information that was requested from the user by the Buy Button.
 * @param paymentCredential: Payment credentials.
 * @param amount: Total amount of transaction.
 * @param shippingOptionId: The option_id of the selected shipping option sent via the checkout update callback.
 *        Only applicable for flexible payments.
 */
data class Payment(val payload: String,
                   val requestedUserInfo: UserInfo,
                   val paymentCredential: PaymentCredential,
                   val amount: Amount,
                   val shippingOptionId: String?)

/**
 * Data in this object will depend on the requested user information defined on the Buy Button.
 *
 * @param shippingAddress: Person's shipping address
 * @param contactName: Person's name
 * @param contactEmail: Person's email
 * @param contactPhone: Person's phone number
 */
data class UserInfo(val shippingAddress: ShippingAddress,
                    val contactName: String?,
                    val contactEmail: String?,
                    val contactPhone: String?)

enum class PaymentProviderType {
    @SerializedName("stripe")
    STRIPE,
    @SerializedName("paypal")
    PAYPAL,
    @SerializedName("token")
    TOKEN
}

/**
 * @param providerType: Payment provider type (one of stripe/paypal/token)
 * @param chargeId: Payment provider charge id (for stripe/paypal, null for tokenization), for a test_payment,
 *                  the charge id will be test_charge_id_12345
 * @param tokenizedCard: PGP-signed tokenized charge card (null for stripe/paypal)
 * @param tokenizedCvv: PGP-signed CVV number (null for stripe/paypal)
 * @param tokenExpiryMonth: Expiry month (null for stripe/paypal)
 * @param tokenExpiryYear: Expiry year (null for stripe/paypal)
 * @param fbPaymentId: A facebook issued payment id for tracking. If it is a test payment, the id will be test_payment_id_12345.
 */
data class PaymentCredential(val providerType: PaymentProviderType,
                             val chargeId: String?,
                             val tokenizedCard: String?,
                             val tokenizedCvv: String?,
                             val tokenExpiryMonth: String?,
                             val tokenExpiryYear: String?,
                             val fbPaymentId: String?)

/**
 * @property currency: Currency for price.
 * @property isTestPayment: Whether this is a test payment. Once set to true, the charge will be a dummy charge.
 * @property merchantName: Name of merchant
 * @property requestedUserInfo: Information requested from person that will render in the dialog.
 * @property priceList: List of objects used to calculate total price. Each label is rendered as a line item in the checkout dialog.
 */
data class PaymentSummary(val currency: String,
                          val isTestPayment: Boolean? = null,
                          val paymentType: PaymentType,
                          val merchantName: String,
                          val requestedUserInfo: List<PaymentRequestedUserInfo>,
                          val priceList: List<LabelPrice>)

/**
 * @property label: Label for line item.
 * @property amount: Amount of line item.
 */
data class LabelPrice(val label: String,
                      val amount: BigDecimal)

enum class PaymentRequestedUserInfo {
    shipping_address, contact_name, contact_phone, contact_email
}

enum class PaymentType {
    FIXED_AMOUNT, FLEXIBLE_AMOUNT
}

data class Amount(val currency: String,
                  val amount: BigDecimal)

/**
 * @property payload: Metadata defined in the Buy Button.
 * @property shippingAddress: The person's shipping address.
 */
data class CheckoutInfo(val payload: String,
                        val shippingAddress: ShippingAddress)


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
