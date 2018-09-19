package me.smu.bot.facebook.model.data

sealed class Referral

data class MeLinkReferral(
        val ref: String,
        val source: String = "SHORTLINK",
        val type: String
) : Referral()

data class AdReferral(
        val ref: String,
        val adId: String = "ADS",
        val source: String,
        val type: String
) : Referral()

data class MessengerCodeReferral(
        val ref: String,
        val source: String = "MESSENGER_CODE",
        val type: String
) : Referral()

data class DiscoverTabReferral(
        val source: String = "DISCOVER_TAB",
        val type: String
) : Referral()

data class ChatPluginReferral(
        val ref: String,
        val source: String = "CUSTOMER_CHAT_PLUGIN",
        val type: String,
        val refererUri: String
) : Referral()