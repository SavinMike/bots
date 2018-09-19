package me.smu.bot.facebook.model.network

import com.google.gson.FieldNamingPolicy
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.post
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.forms.*
import io.ktor.http.*

class FacebookHttpService(val accessToken: String) {
    val client: HttpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer {
                setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            }
        }
    }

    suspend inline fun <B, reified R> post(method: String, body: B): R {
        return client.post {
            url {
                protocol = URLProtocol.HTTPS
                host = "graph.facebook.com"
                encodedPath = "v2.6/me/$method"
                parameters.append("access_token", accessToken)
            }
            contentType(ContentType.Application.Json)
            this.body = body as Any
        }
    }

    suspend fun <R> multipart(method: String, formBuilder: FormBuilder.() -> Unit): R? {
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

        return null//response.response.readBytes()
    }
}