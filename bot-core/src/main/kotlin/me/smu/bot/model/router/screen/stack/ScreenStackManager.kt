package me.smu.bot.model.router.screen.stack

import me.smu.bot.model.router.screen.Screen

class ScreenStackManager(val chatId: Long) {

    private val screens = mutableListOf<Screen>()

    val currentScreen: Screen?
        get() {
            synchronized(chatId) {
                return if (screens.isEmpty()) {
                    null
                } else {
                    screens.last()
                }
            }
        }

    fun addScreen(screen: Screen, screenStrategy: ScreenStrategy) {
        synchronized(chatId) {
            screenStrategy.apply(screen, screens)
        }
    }

    fun pop(screenStrategy: BackScreenStrategy) {
        synchronized(chatId) {
            screenStrategy.apply(screens)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScreenStackManager

        if (chatId != other.chatId) return false

        return true
    }

    override fun hashCode(): Int {
        return chatId.hashCode()
    }
}