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

data class Amount(val currency: String,
                  val amount: BigDecimal)