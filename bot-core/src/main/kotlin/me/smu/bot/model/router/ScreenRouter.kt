package me.smu.bot.model.router

import me.smu.bot.model.router.screen.Screen
import me.smu.bot.model.router.screen.stack.BackScreenStrategy
import me.smu.bot.model.router.screen.stack.ScreenStrategy
import me.smu.bot.model.router.screen.stack.ScreenStrategyProvider
import me.smu.bot.model.sope.Scope

interface ScreenRouter {
    /**
     * @see ScreenStrategyProvider
     */
    val screenStrategyProvider: ScreenStrategyProvider

    fun currentScreen(chatId: Long): Screen?

    /**
     * Go to provided screen by using provided screenStrategy
     * @param screen - Screen which should be shown for user
     * @param newInstance -  true if should recreate screen viewer or false if it should restore previous instance.
     * @param screenStrategy - Strategy for adding screen to stack. By default it is using screenStrategyProvider property
     *
     * @return true if screen successfully add to stack, false otherwise
     * @see ScreenStrategy
     */
    fun <S : Screen> goToScreen(screen: S, newInstance: Boolean = true, screenStrategy: ScreenStrategy = screenStrategyProvider.provideScreenStrategy(screen)): Boolean

    /**
     * Try to go back from current screen. By default it should use screenStrategyProvider as provider BackScreenStrategy instance for given screen
     *
     * @param chatId - chat id which should go back
     * @return true if screen successfully removed from stack, false otherwise
     */
    fun goBack(chatId: Long, backScreenStrategy: BackScreenStrategy? = null): Boolean

    /**
     * Try to restart current screen for chat
     *
     * @param chatId - chat id which should go back
     * @return true if screen successfully restarted, false otherwise
     */
    fun restart(chatId: Long): Boolean

    fun <T : Scope> finishScope(chatId: Long, scope: Class<T>, replaceScreen: Boolean = true): Boolean
}

inline fun <reified S : Screen> Long.provideScreen(screenRouter: ScreenRouter): S? {
    return screenRouter.currentScreen(this) as? S
}