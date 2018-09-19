package me.smu.bot.facebook.model.exception

import me.smu.bot.model.exception.BotException

class ApiException(throwable: Throwable): BotException(cause = throwable)

