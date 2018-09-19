package me.smu.bot.facebook.model.data.callback

import me.smu.bot.facebook.model.data.User

/**
 * An app will receive this callback when a policy enforcement action is taken on the page it manages.
 *
 * A policy enforcement will be taken on a page if it does not conform to Messenger Platform policy,
 * fails to meet Facebook community standards or violates Facebook Pages guidelines. Common issues include spams,
 * sending inappropriate messages (porn, suicide, etc), abusing tags, etc.

 */
data class MessagingPolicyEnforcementEvent(override val sender: User?,
                                           override val recipient: User,
                                           override val timestamp: Long,
                                           val policyEnforcement: PolicyEnforcement): WebhookEvent

enum class PolicyAction{
    BLOCK,
    UNBLOCK
}

/**
 * @property action: action will be either block or unblock
 * @property reason: The reason for being blocked. This field is absent if action is unblock
 */
data class PolicyEnforcement(val action: PolicyAction,
                             val reason: String)