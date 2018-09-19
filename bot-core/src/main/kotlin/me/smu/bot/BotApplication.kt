package me.smu.bot

import me.smu.bot.model.engine.BotEngine
import me.smu.bot.model.engine.BotEngineFactory
import me.smu.bot.model.router.ScreenRouter

class BotApplication(private val router: ScreenRouter,
                     botEngineFactory: BotEngineFactory<*>) {

    class Builder {
        lateinit var screenRouter: ScreenRouter
        lateinit var botEngineFactory: BotEngineFactory<*>

        private fun build(): BotApplication {
            return BotApplication(router = screenRouter, botEngineFactory = botEngineFactory)
        }

        fun build(body: BotApplication.Builder.() -> Unit): BotApplication {
            body()
            return build()
        }
    }

    private val engine: BotEngine = botEngineFactory.createEngine()

    init {
    }

    fun startPolling() {
        engine.startPolling(router)
    }
}

fun bot(body: BotApplication.Builder.() -> Unit) = BotApplication.Builder().build(body)