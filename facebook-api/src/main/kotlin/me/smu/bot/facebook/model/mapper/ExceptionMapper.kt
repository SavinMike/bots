package me.smu.bot.facebook.model.mapper

import me.smu.bot.model.exception.BotException

fun Throwable.mapToBotException(): BotException {
    if (this is BotException) {
        return this
    }

    return BotException(cause = this)
}