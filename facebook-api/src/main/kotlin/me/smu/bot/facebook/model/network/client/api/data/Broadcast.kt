package me.smu.bot.facebook.model.network.client.api.data

import me.smu.bot.facebook.model.network.client.api.data.base.FacebookResponse

data class CreateMessageCreativeRequest(val messages: List<SendingMessage>)

data class CreateMessageCreativeResponse(val messageCreativeId: String) : FacebookResponse

/**
 * @property messageCreativeId: The [messageCreativeId] of the message creative to send in the broadcast.
 * @property scheduleTime: To schedule a broadcast, specify the [scheduleTime] property when you make the API request
 * to send the message. ISO-8601 and Unix timestamp formats are accepted. For example 2018-04-05T20:39:13+00:00 and 1522960753
 * both represent 8:39:13pm 04/05/2018 (UTC)
 * @property notificationType: Push notification type. Defaults to REGULAR.
 */
data class SendBroadcastRequest(val messageCreativeId: String,
                                val scheduleTime: String? = null,
                                val notificationType: NotificationType) {
    val messageType: MessageType = MessageType.MESSAGE_TAG
    val tag: String = "NON_PROMOTIONAL_SUBSCRIPTION"
}

/**
 * @property broadcastId: The unique ID of the sent broadcast.
 */
data class SendBroadcastResponse(val broadcastId: String) : FacebookResponse

data class CancelScheduledBroadcastRequest(val operation: String = "cancel")