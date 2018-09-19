package me.smu.bot.facebook.model.network.api.data.response

data class ErrorResponse(val error: FacebookError): FacebookResponse()

data class FacebookError(val message: String?,
                         val type: String?,
                         val code: Int,
                         val errorSubcode: Int,
                         val fbtraceId: String)
