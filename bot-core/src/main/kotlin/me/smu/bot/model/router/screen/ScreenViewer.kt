package me.smu.bot.model.router.screen


typealias ScreenViewer = (screen: Screen, newInstance: Boolean) -> Boolean

typealias ScreenProvider = (chatId: Long) -> Screen?

