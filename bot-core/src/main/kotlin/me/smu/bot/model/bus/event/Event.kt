package me.smu.bot.model.bus.event

import me.smu.bot.model.router.screen.Screen
import me.smu.bot.model.sope.Scope

interface Event {
    val chatId: Long
}

data class FinishScopeEvent constructor(override val chatId: Long,
                                        val scope: Scope,
                                        val replaceScreen: Boolean = true) : Event

data class ChangeScreenEvent(override val chatId: Long,
                             val prevScreen: Screen?,
                             val currentScreen: Screen) : Event
