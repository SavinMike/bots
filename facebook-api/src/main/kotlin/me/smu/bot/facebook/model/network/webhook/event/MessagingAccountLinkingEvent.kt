package me.smu.bot.facebook.model.network.webhook.event

import com.google.gson.annotations.SerializedName
import me.smu.bot.facebook.model.data.User

/**
 * When using Account Linking, this callback will occur when the Link Account or Unlink Account button have been tapped.
 */
data class MessagingAccountLinkingEvent(override val sender: User,
                                        override val recipient: User,
                                        override val timestamp: Long,
                                        val accountLinking: AccountLinkingInfo) : WebhookEvent

enum class AccountLinkingStatus {
    @SerializedName("linked")
    LINKED,
    @SerializedName("unlinked")
    UNLINKED
}

/**
 * @property status: linked or unlinked
 * @property authorizationCode: Value of pass-through authorization_code provided in the Account Linking flow
 */
data class AccountLinkingInfo(val status: AccountLinkingStatus,
                              val authorizationCode: String?)