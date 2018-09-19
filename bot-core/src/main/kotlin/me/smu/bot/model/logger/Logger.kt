package me.smu.bot.model.logger

interface Logger {
    val defaultTag: String
        get() = "BotBuilder"

    fun log(tag: String = defaultTag, message: String)

    fun log(tag: String = defaultTag, error: Throwable, message: String = "")
}

object DefaultLogger : Logger {
    override fun log(tag: String, message: String) {
        println("$tag: $message")
    }

    override fun log(tag: String, error: Throwable, message: String) {
        System.err.println("$tag: $message")
        error.printStackTrace()
    }

}