package me.smu.bot.facebook.model.network.webhook

import com.google.gson.FieldNamingPolicy
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import me.smu.bot.facebook.model.dispatcher.UpdateDispatcher
import me.smu.bot.facebook.model.exception.ApiException
import me.smu.bot.facebook.model.mechanism.Webhook
import me.smu.bot.facebook.model.network.webhook.gson.PageEntityTypeAdapter
import me.smu.bot.facebook.model.network.webhook.gson.ServerRequestTypeAdapter
import me.smu.bot.model.logger.DefaultLogger
import me.smu.bot.model.logger.Logger
import org.slf4j.event.Level

internal class WebhookServer(webhook: Webhook,
                             verifyToken: String,
                             private val dispatcher: UpdateDispatcher,
                             private val logger: Logger = DefaultLogger) {
    private val server: ApplicationEngine

    init {
        server = embeddedServer(factory = webhook.applicationEngineFactory, port = webhook.port, host = webhook.host) {
            install(CallLogging) {
                level = Level.DEBUG
                filter { true }
            }
            install(ContentNegotiation) {
                gson {
                    setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    registerTypeAdapter(MessagingEntry::class.java, PageEntityTypeAdapter)
                    registerTypeAdapter(WebhookRequest::class.java, ServerRequestTypeAdapter)
                }
            }
            routing {
                get(path = "webhook") {
                    val params = call.request.queryParameters
                    val mode = params["hub.mode"]
                    val token = params["hub.verify_token"]
                    val challenge = params["hub.challenge"]

                    // Checks if a token and mode is in the query string of the request
                    if (mode != null && token != null) {

                        // Checks the mode and token sent is correct
                        if (mode == "subscribe" && token == verifyToken) {
                            // Responds with the challenge token from the request
                            logger.log(message = "WEBHOOK_VERIFIED")
                            call.response.status(HttpStatusCode.OK)
                            call.respondText { challenge ?: "" }
                        } else {
                            // Responds with '403 Forbidden' if verify tokens do not match
                            call.response.status(HttpStatusCode.Forbidden)
                        }
                    }
                }
                post("/webhook") {

                    try {
                        val body = call.receive<WebhookRequest<*>>()
                        // Returns a '200 OK' response to all requests
                        call.response.status(HttpStatusCode.OK)
                        call.respondText { "EVENT_RECEIVED" }
                        when (body) {
                            is PageServiceRequest -> {
                                body.entry.forEach {
                                    it.messaging.forEach {
                                        dispatcher.handleDialog(it)
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        // Returns a '404 Not Found' if event is not from a page subscription
                        dispatcher.handleError(ApiException(throwable = e))
                        call.response.status(HttpStatusCode.Forbidden)
                    }

                }
            }
        }
    }

    fun start(wait: Boolean) {
        server.start(wait = wait)
    }

}