package me.smu.bot.model.bus

import me.smu.bot.model.bus.event.Event
import me.smu.bot.model.router.ScreenRouter

interface EventInterceptor {
    fun intercept(router: ScreenRouter, event: Event): Boolean
}

object EmptyEventInterceptor: EventInterceptor {
    override fun intercept(router: ScreenRouter, event: Event): Boolean {
        return false
    }
}