package me.smu.bot.model.router.screen.stack

import me.smu.bot.model.router.screen.Screen

interface ScreenStrategyProvider {
    fun provideScreenStrategy(screen: Screen): ScreenStrategy

    fun provideBackScreenStrategy(screen: Screen): BackScreenStrategy
}