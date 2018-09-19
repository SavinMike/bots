package me.smu.bot.model.router.screen

import me.smu.bot.model.sope.MainScope
import me.smu.bot.model.sope.Scope

interface Screen {
    val chatId: Long
    val scope: Scope
        get() = MainScope
}

inline fun <reified S : Scope> Screen.provideScope(): S? {
    return (scope as? S).let {
        if (it != null) {
            it
        } else {
            println("Current screen is $this")
            println("Try to get scope ${S::class.java} but current scope is ${scope::class.java}")
            null
        }
    }
}
