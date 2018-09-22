package me.smu.bot.model.engine

import me.smu.bot.model.router.ScreenRouter

interface BotEngine {
    fun start(wait: Boolean, router: ScreenRouter)
}

interface BotEngineLifecycle {
    fun init(botEngine: BotEngine)
    fun preStartingPolling(botEngine: BotEngine)
}

object SkipBotEngineLifecycle : BotEngineLifecycle {
    override fun init(botEngine: BotEngine) {

    }

    override fun preStartingPolling(botEngine: BotEngine) {
    }

}

interface BotEngineFactory<out T : BotEngine> {
    val botEngineLifecycle: BotEngineLifecycle
    fun createEngine(): T
}