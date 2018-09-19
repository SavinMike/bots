package me.smu.bot.facebook.model.network.client.api.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import me.smu.bot.facebook.model.network.client.api.data.base.EmptyFacebookResponse
import me.smu.bot.facebook.model.network.client.api.data.base.ErrorResponse
import me.smu.bot.facebook.model.network.client.api.data.base.FacebookError
import me.smu.bot.facebook.model.network.client.api.data.base.FacebookResponse
import me.smu.bot.facebook.model.network.webhook.gson.deserializeInst
import java.lang.reflect.Type

object FacebookResponseDeserializer : JsonDeserializer<FacebookResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): FacebookResponse {
        if (json.isJsonObject) {
            val jsonObject = json.asJsonObject
            return when {
                jsonObject.has("error") -> ErrorResponse(context.deserializeInst<FacebookError>(jsonObject["error"]))
                else -> EmptyFacebookResponse
            }
        }

        return EmptyFacebookResponse
    }
}