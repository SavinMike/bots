package me.smu.bot

import me.ivmg.telegram.Bot
import me.ivmg.telegram.bot
import me.ivmg.telegram.dispatch
import me.ivmg.telegram.dispatcher.Dispatcher
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

class TelegramBotEngine(private val token: String,
                        private var botEngineLifecycle: BotEngineLifecycle) : BotEngine {

    lateinit var telegramBot: Bot

    var dispatcher: Dispatcher.(router: ScreenRouter) -> Unit = {}
    var eventInterceptor: EventInterceptor = EmptyEventInterceptor

    init {
        botEngineLifecycle.init(this)
    }

    override fun start(wait: Boolean, router: ScreenRouter) {
        botEngineLifecycle.preStartingPolling(this)
        telegramBot = bot {
            token = this@TelegramBotEngine.token
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

        telegramBot.startPolling()
    }
}

fun telegramFactory(init: TelegramBotEngineFactory.() -> Unit): BotEngineFactory<TelegramBotEngine> = TelegramBotEngineFactory().apply(init)

fun TelegramBotEngineFactory.setEventInterceptor(eventInterceptor: (ScreenRouter, Event) -> Boolean): BotEngineFactory<TelegramBotEngine> {
    this.eventInterceptor = object : EventInterceptor {
        override fun intercept(router: ScreenRouter, event: Event): Boolean {
            return eventInterceptor(router, event)
        }
    }

    return this
}

class TelegramBotEngineFactory : BotEngineFactory<TelegramBotEngine> {

    var dispatcher: Dispatcher.(router: ScreenRouter) -> Unit = {}
    lateinit var token: String
    override var botEngineLifecycle: BotEngineLifecycle = SkipBotEngineLifecycle
    var eventInterceptor: EventInterceptor = EmptyEventInterceptor

    override fun createEngine(): TelegramBotEngine {
        return TelegramBotEngine(token, botEngineLifecycle).apply {
            dispatcher = this@TelegramBotEngineFactory.dispatcher
            eventInterceptor = this@TelegramBotEngineFactory.eventInterceptor
        }
    }
}

