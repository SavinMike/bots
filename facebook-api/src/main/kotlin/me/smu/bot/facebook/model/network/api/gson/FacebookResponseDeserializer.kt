package me.smu.bot.facebook.model.network.api.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import me.smu.bot.facebook.model.network.api.data.response.ErrorResponse
import me.smu.bot.facebook.model.network.api.data.response.FacebookError
import me.smu.bot.facebook.model.network.api.data.response.FacebookResponse
import java.lang.reflect.Type

object FacebookResponseDeserializer : JsonDeserializer<FacebookResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): FacebookResponse {
        if (json.isJsonObject) {
            val jsonObject = json.asJsonObject
            return when {
                jsonObject.has("error") -> ErrorResponse(context.deserializeInst<FacebookError>(jsonObject["error"]))
                else -> FacebookResponse()
            }
        }

        return FacebookResponse()
    }
}