package me.smu.bot.facebook.model.data

/**
 * @param privacyUrl: The URL of the privacy policy for your bot. Required for buy button payments.
 * @param publicKey: Your public key. Used to encrypt all webview payments, and buy button implementations that use tokenized payments.
 * @param testers: A list of IDs for people that will test payments in your bot. These people will send a mock payment
 * when they tap the buy button.
 */
data class PaymentSettings(val privacyUrl: String? = null,
                           val publicKey: String? = null,
                           val testers: List<Long>? = null)