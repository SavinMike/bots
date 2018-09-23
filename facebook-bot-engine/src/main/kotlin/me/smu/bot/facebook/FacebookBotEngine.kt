package me.smu.bot.facebook

import me.smu.bot.facebook.model.dispatcher.UpdateDispatcher
import me.smu.bot.facebook.model.mechanism.WebHookBuilder
import me.smu.bot.facebook.model.mechanism.Webhook
import me.smu.bot.model.bus.EmptyEventInterceptor
import me.smu.bot.model.bus.EventBus
import me.smu.bot.model.bus.EventInterceptor
import me.smu.bot.model.bus.event.Event
import me.smu.bot.model.bus.event.FinishScopeEvent
import me.smu.bot.model.engine.BotEngine
import me.smu.bot.model.engine.BotEngineFactory
import me.smu.bot.model.engine.BotEngineLifecycle
import me.smu.bot.model.engine.SkipBotEngineLifecycle
import me.smu.bot.model.router.ScreenRouter

class FacebookBotEngine(private val webhook: Webhook,
                        private val accessToken: String,
                        private var botEngineLifecycle: BotEngineLifecycle) : BotEngine {

    lateinit var facebookBot: FacebookBot

    var dispatcher: UpdateDispatcher.(router: ScreenRouter) -> Unit = {}
    var eventInterceptor: EventInterceptor = EmptyEventInterceptor

    init {
        botEngineLifecycle.init(this)
    }

    override fun start(wait: Boolean, router: ScreenRouter) {
        botEngineLifecycle.preStartingPolling(this)
        facebookBot = facebookBot {
            webhook = this@FacebookBotEngine.webhook
            accessToken = this@FacebookBotEngine.accessToken
            dispatch {
                dispatcher(router)
            }
        }

        EventBus.addEventEmitter { event ->
            if (eventInterceptor.intercept(router, event)) {
                return@addEventEmitter
            }

            when (event) {
                is FinishScopeEvent -> router.finishScope(event.chatId, event.scope::class.java, event.replaceScreen)
            }
        }

        facebookBot.startWebHook(wait)
    }
}

fun facebook(init: FacebookBotEngineFactory.() -> Unit): BotEngineFactory<FacebookBotEngine> = FacebookBotEngineFactory().apply(init)

fun FacebookBotEngineFactory.setEventInterceptor(eventInterceptor: (ScreenRouter, Event) -> Boolean): BotEngineFactory<FacebookBotEngine> {
    this.eventInterceptor = object : EventInterceptor {
        override fun intercept(router: ScreenRouter, event: Event): Boolean {
            return eventInterceptor(router, event)
        }
    }

    return this
}

class FacebookBotEngineFactory : BotEngineFactory<FacebookBotEngine> {

    var dispatcher: UpdateDispatcher.(router: ScreenRouter) -> Unit = {}
    lateinit var accessToken: String
    lateinit var webHookBuilder: WebHookBuilder.() -> Unit
    override var botEngineLifecycle: BotEngineLifecycle = SkipBotEngineLifecycle
    var eventInterceptor: EventInterceptor = EmptyEventInterceptor

    override fun createEngine(): FacebookBotEngine {
        return FacebookBotEngine(WebHookBuilder().apply(webHookBuilder).build(), accessToken, botEngineLifecycle).apply {
            dispatcher = this@FacebookBotEngineFactory.dispatcher
            eventInterceptor = this@FacebookBotEngineFactory.eventInterceptor
        }
    }
}

