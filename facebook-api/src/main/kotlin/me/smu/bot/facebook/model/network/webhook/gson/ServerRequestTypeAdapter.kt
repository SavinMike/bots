package me.smu.bot.facebook.model.network.webhook.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import me.smu.bot.facebook.model.network.webhook.MessagingEntry
import me.smu.bot.facebook.model.network.webhook.PageServiceRequest
import me.smu.bot.facebook.model.network.webhook.WebhookRequest
import me.smu.bot.facebook.model.network.webhook.event.*
import java.lang.reflect.Type

object ServerRequestTypeAdapter : JsonDeserializer<WebhookRequest<*>> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): WebhookRequest<*> {
        val jsonObject = json.asJsonObject
        print(jsonObject.toString())
        if (jsonObject.has("object")) {
            val objectType = jsonObject.get("object").asString
            when (objectType) {
                "page" -> {
                    val value = object : TypeToken<List<MessagingEntry>>() {}
                    val jsonEntry = jsonObject.getAsJsonArray("entry")
                    return PageServiceRequest(context.deserialize(jsonEntry, value.type))
                }
            }
        }

        throw IllegalArgumentException("Incorrect service request")
    }
}

object PageEntityTypeAdapter : JsonDeserializer<MessagingEntry> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): MessagingEntry {
        val jsonObject = json.asJsonObject
        val id = jsonObject.getAsJsonPrimitive("id").asString
        val time = jsonObject.getAsJsonPrimitive("time").asLong
        if (!jsonObject.has("messaging")) {
            return MessagingEntry(id, time, emptyList())
        }
        val webhookEvents = jsonObject.getAsJsonArray("messaging").map {
            val item = it.asJsonObject
            when {
                item.has("message") -> context.deserializeInst<MessagesEvent>(item)
                item.has("delivery") -> context.deserializeInst<MessageDeliveriesEvent>(item)
                item.has("read") -> context.deserializeInst<MessageReadsEvent>(item)
                item.has("account_linking") -> context.deserializeInst<MessagingAccountLinkingEvent>(item)
                item.has("checkout_update") -> context.deserializeInst<MessagingCheckoutUpdatesEvent>(item)
                item.has("game_play") -> context.deserializeInst<MessagingGamePlaysEvent>(item)
                item.has("optin") -> context.deserializeInst<MessagingOptinsEvent>(item)
                item.has("policy_enforcement") -> context.deserializeInst<MessagingPolicyEnforcementEvent>(item)
                item.has("postback") -> context.deserializeInst<MessagingPostbacksEvent>(item)
                item.has("pre_checkout") -> context.deserializeInst<MessagingPreCheckoutsEvent>(item)
            //todo add MessagingHandoversEvent
                else -> context.deserializeInst<UnknownWebhookEvent>(json)
            }
        }
        return MessagingEntry(id, time, webhookEvents)
    }
}

inline fun <reified T> JsonDeserializationContext.deserializeInst(item: JsonElement) = deserialize<T>(item, T::class.java)