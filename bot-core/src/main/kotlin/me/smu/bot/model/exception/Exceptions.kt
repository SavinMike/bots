package me.smu.bot.model.exception

open class BotException(message: String? = null,
                        cause: Throwable? = null) : Exception(message, cause)