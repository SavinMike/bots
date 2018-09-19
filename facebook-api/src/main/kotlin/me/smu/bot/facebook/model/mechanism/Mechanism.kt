package me.smu.bot.facebook.model.mechanism

import io.ktor.server.engine.ApplicationEngineFactory

data class Webhook constructor(val port: Int = 80,
                               val host: String = "0.0.0.0",
                               val applicationEngineFactory: ApplicationEngineFactory<*, *>)

class WebHookBuilder(var port: Int = 80,
                     var host: String = "0.0.0.0") {

    lateinit var applicationEngineFactory: ApplicationEngineFactory<*, *>

    fun build(): Webhook = Webhook(port, host, applicationEngineFactory)
}



