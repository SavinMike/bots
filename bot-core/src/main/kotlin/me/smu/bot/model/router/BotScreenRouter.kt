package me.smu.bot.model.router

import me.smu.bot.BotApplication
import me.smu.bot.model.router.screen.Screen
import me.smu.bot.model.router.screen.ScreenProvider
import me.smu.bot.model.router.screen.ScreenViewer
import me.smu.bot.model.router.screen.stack.AddToEndScreenStrategy
import me.smu.bot.model.router.screen.stack.BackScreenStrategy
import me.smu.bot.model.router.screen.stack.ClearScopeScreenStrategy
import me.smu.bot.model.router.screen.stack.ClearScreenStrategy
import me.smu.bot.model.router.screen.stack.RemoveCurrentScreenStrategy
import me.smu.bot.model.router.screen.stack.ScreenStackManager
import me.smu.bot.model.router.screen.stack.ScreenStrategy
import me.smu.bot.model.router.screen.stack.ScreenStrategyProvider
import me.smu.bot.model.sope.Scope

fun BotApplication.Builder.router(body: BotScreenRouter.Builder.() -> Unit) {
    screenRouter = BotScreenRouter.Builder().build(body)
}

fun BotScreenRouter.Builder.screen(screenViewer: ScreenViewer) {
    this.screenViewer = screenViewer
}

fun BotScreenRouter.Builder.emptySessionScreen(screenProvider: ScreenProvider) {
    emptySessionScreenProvider = screenProvider
}

fun BotScreenRouter.Builder.welcomeScreen(screenProvider: ScreenProvider) {
    startScreenProvider = screenProvider
}

fun BotScreenRouter.Builder.screenStrategyProvider(screenStrategyProvider: ScreenStrategyProvider) {
    this.screenStrategyProvider = screenStrategyProvider
}

open class BotScreenRouter constructor(private val screenViewer: ScreenViewer,
                                       private val emptySessionScreenProvider: ScreenProvider = { null },
                                       private val startScreenProvider: ScreenProvider = { null },
                                       override val screenStrategyProvider: ScreenStrategyProvider) : ScreenRouter {

    private val screenStackManagers: MutableCollection<ScreenStackManager> = mutableSetOf()

    override fun currentScreen(chatId: Long): Screen? {
        return screenStackManagers
            .firstOrNull { it.chatId == chatId }?.currentScreen
    }

    override fun <S : Screen> goToScreen(screen: S, newInstance: Boolean, screenStrategy: ScreenStrategy): Boolean {
        if (screenViewer(screen, newInstance)) {
            val screenStackManager = screenStackManagers.firstOrNull { it.chatId == screen.chatId }
                    ?: screenStackManagers.run {
                        val screenStackManager = ScreenStackManager(screen.chatId)
                        screenStackManagers.add(screenStackManager)
                        screenStackManager
                    }

            screenStackManager.addScreen(screen, screenStrategy)

            return true
        }

        return false
    }

    override fun restart(chatId: Long): Boolean {
        val screenStackManager = screenStackManagers.firstOrNull { it.chatId == chatId }
        return screenStackManager?.currentScreen?.let {
            goToScreen(it, true)
        } ?: false
    }

    override fun <T : Scope> finishScope(chatId: Long, scope: Class<T>, replaceScreen: Boolean): Boolean {
        return popStack(chatId, ClearScopeScreenStrategy(scope), replaceScreen)
    }

    override fun goBack(chatId: Long): Boolean {
        val screenStackManager = screenStackManagers.firstOrNull { it.chatId == chatId }
        val currentScreen = screenStackManager?.currentScreen

        val screenStrategy = if (currentScreen == null) null else screenStrategyProvider.provideBackScreenStrategy(currentScreen)

        return popStack(chatId, backScreenStrategy = screenStrategy)
    }

    private fun popStack(chatId: Long, backScreenStrategy: BackScreenStrategy?, replaceScreen: Boolean = true): Boolean {
        val screenStackManager = screenStackManagers.firstOrNull { it.chatId == chatId }
        val currentScreen = screenStackManager?.currentScreen

        if (screenStackManager == null || currentScreen == null || backScreenStrategy == null) {
            val screen = emptySessionScreenProvider(chatId) ?: startScreenProvider(chatId) ?: return false
            goToScreen(screen, newInstance = true, screenStrategy = ClearScreenStrategy)
            return true
        }

        screenStackManager.pop(backScreenStrategy)

        if (!replaceScreen) {
            return true
        }

        if (currentScreen === screenStackManager.currentScreen) {
            return true
        }

        return screenStackManager.currentScreen?.let {
            goToScreen(it, false)
        } ?: startScreenProvider(chatId)?.let {
            goToScreen(it, true)
        } ?: false
    }

    class Builder {
        lateinit var screenViewer: ScreenViewer
        internal var emptySessionScreenProvider: ScreenProvider = { null }
        internal var startScreenProvider: ScreenProvider = { null }
        internal var screenStrategyProvider: ScreenStrategyProvider = object : ScreenStrategyProvider {
            override fun provideBackScreenStrategy(screen: Screen): BackScreenStrategy {
                return RemoveCurrentScreenStrategy
            }

            override fun provideScreenStrategy(screen: Screen): ScreenStrategy {
                return AddToEndScreenStrategy
            }
        }

        private fun build(): ScreenRouter {
            return BotScreenRouter(
                screenViewer = screenViewer,
                emptySessionScreenProvider = emptySessionScreenProvider,
                startScreenProvider = startScreenProvider,
                screenStrategyProvider = screenStrategyProvider)
        }

        fun build(body: Builder.() -> Unit): ScreenRouter {
            body()
            return build()
        }
    }
}
