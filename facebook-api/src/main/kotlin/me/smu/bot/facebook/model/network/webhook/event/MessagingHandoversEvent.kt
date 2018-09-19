package me.smu.bot.facebook.model.network.webhook.event

import me.smu.bot.facebook.model.data.User

/**
 * The messaging_handovers webhook event is used to notify your webhook when certain actions are taken using the Messenger Platform's
 * handover protocol, including PassThreadControl, TakeThreadControl, and RoleChange events.
 *
 * PassThreadControl: This callback will occur when thread ownership for a user has been passed to your application.
 *
 * TakeThreadControl: This callback will occur when thread ownership for a user has been taken away from your application.
 *
 * RequestThreadControl: This callback will be sent to the Primary Receiver app when a Secondary Receiver app calls the
 * Request Thread Control API. The Primary Receiver may then choose to honor the request and pass thread control, or
 * ignore the request.
 *
 * RoleChange: This callback will occur when a page admin changes the role of your application.
 * An app can be assigned the roles of primary_receiver or secondary_receiver.
 *
 * @property newOwnerAppId: App ID that thread control is passed to.
 * @property requestedOwnerAppId: App ID of the Secondary Receiver that is requesting thread control.
 * @property previousOwnerAppId: App ID that thread control was taken from.
 * @property metadata: Custom string specified in the API request.
 */
data class MessagingHandoversEvent(override val sender: User,
                                   override val recipient: User,
                                   override val timestamp: Long,
                                   val newOwnerAppId: String?,
                                   val previousOwnerAppId: String?,
                                   val requestedOwnerAppId: String?,
                                   val appRoles: Map<String, String>?,
                                   val metadata: String): WebhookEvent

