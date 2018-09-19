package me.smu.bot.facebook.model.network.api.data

import com.google.gson.annotations.SerializedName
import me.smu.bot.facebook.model.data.Message
import me.smu.bot.facebook.model.data.User

data class SendMessageRequest(val messageType: MessageType,
                              val recipient: User,
                              val message: Message)


enum class MessageType {
    /**
     * Message is in response to a received message. This includes promotional and non-promotional messages sent inside
     * the 24-hour standard messaging window or under the 24+1 policy. For example, use this tag to respond if a person
     * asks for a reservation confirmation or an status update.
     */
    @SerializedName("RESPONSE") RESPONSE,

    /**
     * Message is being sent proactively and is not in response to a received message. This includes promotional and
     * non-promotional messages sent inside the the 24-hour standard messaging window or under the 24+1 policy.
     */
    @SerializedName("UPDATE") UPDATE,

    /**
     * Message is non-promotional and is being sent outside the 24-hour standard messaging window with a message tag.
     * The message must match the allowed use case for the tag.
     */
    @SerializedName("MESSAGE_TAG") MESSAGE_TAG
}