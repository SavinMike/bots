package me.smu.bot.facebook.model.network.webhook

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import me.smu.bot.facebook.model.network.webhook.event.WebhookEvent

@Serializable
sealed class WebhookRequest<T>(@SerializedName("object")
                               val objectType: String,
                               val entry: List<T>)

class PageServiceRequest(entry: List<MessagingEntry>) : WebhookRequest<MessagingEntry>(entry = entry, objectType = "page")

data class MessagingEntry(val id: String,
                          val time: Long,
                          val messaging: List<WebhookEvent>)