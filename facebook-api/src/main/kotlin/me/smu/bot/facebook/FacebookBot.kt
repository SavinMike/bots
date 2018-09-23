package me.smu.bot.facebook

import me.smu.bot.facebook.model.dispatcher.UpdateDispatcher
import me.smu.bot.facebook.model.mechanism.WebHookBuilder
import me.smu.bot.facebook.model.mechanism.Webhook
import me.smu.bot.facebook.model.network.client.FacebookHttpClient
import me.smu.bot.facebook.model.network.client.api.ApiProvider
import me.smu.bot.facebook.model.network.client.controller.*
import me.smu.bot.facebook.model.network.webhook.WebhookServer

class FacebookBot internal constructor(private val server: WebhookServer,
                                       controllerProvider: ControllerProvider) :
        SendController by controllerProvider.sendController,
        AttachmentUploadController by controllerProvider.attachmentUploadController,
        BroadcastController by controllerProvider.broadcastController,
        ProfileController by controllerProvider.profileController {

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
        val webhookServer = WebhookServer(webhook, verifyToken, dispatcher)
        val facebookHttpService = FacebookHttpClient(accessToken)

        val controllerProvider: ControllerProvider = ApiControllerProvider(ApiProvider(facebookHttpService))
        val facebookBot = FacebookBot(webhookServer, controllerProvider)
        dispatcher.facebookBot = facebookBot
        return facebookBot
    }
}