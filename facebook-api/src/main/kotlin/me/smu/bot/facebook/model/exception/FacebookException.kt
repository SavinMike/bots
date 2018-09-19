package me.smu.bot.facebook.model.exception

import me.smu.bot.facebook.model.network.api.data.response.FacebookError
import me.smu.bot.model.exception.BotException

class ApiException(val error: FacebookError? = null,
                   throwable: Throwable? = null) : BotException(cause = throwable){
    override fun toString(): String {
        return "ApiException(error=$error)"
    }
}

