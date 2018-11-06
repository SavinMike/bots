package me.smu.bot.facebook.model.network.client

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.forms.FormBuilder
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import me.smu.bot.facebook.model.exception.ApiException
import me.smu.bot.facebook.model.network.client.api.data.base.ErrorResponse
import me.smu.bot.facebook.model.network.client.api.data.base.FacebookResponse
import me.smu.bot.facebook.model.network.client.api.gson.FacebookResponseDeserializer

internal class FacebookHttpClient(val accessToken: String) {
    private val gson: Gson = GsonBuilder().apply {
        setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        registerTypeAdapter(FacebookResponse::class.java, FacebookResponseDeserializer)
    }.create()

    val client: HttpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer {
                setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                registerTypeAdapter(FacebookResponse::class.java, FacebookResponseDeserializer)
            }
        }
    }

    @Throws(ApiException::class)
    suspend inline fun <B, reified R : FacebookResponse> post(method: String, body: B): R {
        //todo remove
        println("Send to $method")
        println(gson.toJson(body))
        try {
            val response = client.post<R> {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "graph.facebook.com"
                    encodedPath = "me/$method"
                    parameters.append("access_token", accessToken)
                }
                contentType(ContentType.Application.Json)
                this.body = body as Any
            }

            if (response is ErrorResponse) {
                println("Failed Request: $response")
                throw ApiException(response.error)
            }

            println("Success Request: $response")

            return response
        } catch (e: Exception) {
            println("Exception caught")
            e.printStackTrace()
            throw ApiException()
        }
    }

    suspend fun <R : FacebookResponse> multipart(method: String, formBuilder: FormBuilder.() -> Unit): R? {
        //TODO fix bug with returning value
        @Suppress("UNUSED_VARIABLE")
        val response = client.call {
            url {
                protocol = URLProtocol.HTTPS
                host = "graph.facebook.com"
                encodedPath = "v2.6/me/$method"
                parameters.append("access_token", accessToken)
            }
            contentType(ContentType.Application.Json)
            this.body = MultiPartFormDataContent(formData(formBuilder))
        }

        return null //response.response.readBytes()
    }
}