package me.smu.bot.facebook.model.network.api.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import me.smu.bot.facebook.model.data.callback.WebhookEvent

@Serializable
sealed class ServiceRequest<T>(@SerializedName("object")
                               val objectType: String,
                               val entry: List<T>)

class PageServiceRequest(entry: List<PageEntry>) : ServiceRequest<PageEntry>(entry = entry, objectType = "page")

data class PageEntry(
        val id: String,
        val time: Long,
        val messaging: List<WebhookEvent>)