package me.smu.bot.facebook.model.network.webhook.event

import me.smu.bot.facebook.model.data.User

/**
 * Postbacks occur when a postback button, Get Started button, or persistent menu item is tapped.
 *
 * The payload field passed is defined in the above places.
 *
 *
 */
data class MessagingPostbacksEvent(override val sender: User?,
                                   override val recipient: User,
                                   override val timestamp: Long,
                                   val postback: Postback): WebhookEvent

data class Postback(val title: String? = null,
                    val payload: String? = null,
                    val referral: String? = null)