package me.smu.bot.model.bus.event

import me.smu.bot.model.sope.Scope

interface Event {
    val chatId: Long
}

data class FinishScopeEvent constructor(override val chatId: Long,
                                        val scope: Scope,
                                        val replaceScreen: Boolean = true) : Event

