package me.smu.bot.facebook.model.data.callback

import me.smu.bot.facebook.model.data.User

/**
 * This callback will occur when the send to Messenger plugin has been tapped, a user has accepted a message request
 * using customer matching, or a user has opted in to receive messages via the checkbox plugin.
 *
 * When using the plugin, the optin.ref parameter is set by the data-ref field on the "Send to Messenger" plugin.
 * This field can be used by the developer to associate a click event on the plugin with a callback.
 */
data class MessagingOptinsEvent(override val sender: User?,
                                override val recipient: User,
                                override val timestamp: Long,
                                val optin: Optin) : WebhookEvent

/**
 * @property ref: data-ref attribute that was defined with the entry point.
 * @property userRef: Checkbox plugin only. user_ref attribute that was defined in the checkbox plugin include.
 */
data class Optin(val ref: String,
                 val userRef: String?)