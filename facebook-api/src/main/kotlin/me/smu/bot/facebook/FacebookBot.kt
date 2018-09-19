package me.smu.bot.facebook

import me.smu.bot.facebook.model.Server
import me.smu.bot.facebook.model.data.User
import me.smu.bot.facebook.model.dispatcher.UpdateDispatcher
import me.smu.bot.facebook.model.exception.ApiException
import me.smu.bot.facebook.model.mechanism.WebHookBuilder
import me.smu.bot.facebook.model.mechanism.Webhook
import me.smu.bot.facebook.model.network.FacebookHttpService
import me.smu.bot.facebook.model.network.api.SendApi
import me.smu.bot.facebook.model.network.api.data.MessageType
import me.smu.bot.facebook.model.network.api.data.SendMessageRequest
import me.smu.bot.facebook.model.network.api.data.SendingMessage

class FacebookBot(accessToken: String,
                  verifyToken: String,
                  dispatcher: UpdateDispatcher,
                  webhook: Webhook) {
    private val server = Server(webhook, verifyToken, dispatcher.apply { facebookBot = this@FacebookBot })
    private val httpService = FacebookHttpService(accessToken)

    private val sendApi: SendApi = SendApi(httpService)

    suspend fun sendMessage(recipient: User, text: String) {
        sendApi.sendMessage(SendMessageRequest(MessageType.MESSAGE_TAG, recipient, SendingMessage(text = text)))
    }

    suspend fun sendMessage(recipient: User, message: SendingMessage): Boolean {
        return try {
            sendApi.sendMessage(SendMessageRequest(recipient = recipient, message = message))
            println("sendMessage success")
            true
        } catch (e: ApiException) {
            println("sendMessage failed: $e")
            false
        }
    }

    fun startWebHook(wait: Boolean = true) {
        server.start(wait)
    }
}

fun FacebookBuilder.webhook(block: WebHookBuilder.() -> Unit): Webhook {
    webhook = WebHookBuilder().apply(block).build()
    return webhook
}

fun FacebookBuilder.dispatch(body: UpdateDispatcher.() -> Unit): UpdateDispatcher {
    dispatcher.body()
    return dispatcher
}

fun facebookBot(block: FacebookBuilder.() -> Unit) = FacebookBuilder().apply(block).build()

class FacebookBuilder {
    lateinit var accessToken: String
    lateinit var webhook: Webhook
    var dispatcher: UpdateDispatcher = UpdateDispatcher()
    var verifyToken: String = "TOKEN"


    fun build(): FacebookBot {
        return FacebookBot(accessToken, verifyToken, dispatcher, webhook)
    }
}