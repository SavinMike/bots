package me.smu.bot.samples

import io.ktor.server.netty.Netty
import me.smu.bot.facebook.dispatch
import me.smu.bot.facebook.facebookBot
import me.smu.bot.facebook.model.dispatcher.default
import me.smu.bot.facebook.model.dispatcher.handlers.start
import me.smu.bot.facebook.model.dispatcher.handlers.text
import me.smu.bot.facebook.model.dispatcher.message
import me.smu.bot.facebook.model.dispatcher.payloadEvent
import me.smu.bot.facebook.model.mapper.toRecipient
import me.smu.bot.facebook.webhook

fun main(args: Array<String>) {
    val facebookBot = facebookBot {
        webhook {
            applicationEngineFactory = Netty
            port = 4000
            host = "localhost"
        }
        accessToken = "EAAgbl1lEO9EBABpqkj9lT3duWZC3f67QZApwNJJvtZAJyLj29KQbriHYPShZBZAwom7EabfR4FH24OdaXvRBU9uvT73qrATZCaCrf4HxOvAMi94GJCUYiIvmvOsyoZBx4X3ZCsJ7SSDEWCxiYIurqUEG8EMdUHP2ocB90WPf4rVCZCAZDZD"
        dispatch {
            payloadEvent {
                start { event, facebook ->
                    facebook.sendMessage(event.recipient.toRecipient(), "test")
                    true
                }

                default { event, facebook ->
                    false
                }
            }
            message {
                text { message, facebook ->
                    {
                        facebook.sendMessage(message.sender.toRecipient(), "echo: ${message.message?.text ?: ""}")
                        true
                    }

                }
            }
            default { pageServiceRequest, facebookBot ->
                pageServiceRequest.sender?.let {
                    facebookBot.sendMessage(it.toRecipient(), "echo: ${pageServiceRequest::class.java.simpleName}")
                    true
                } ?: false
            }
        }
    }

    facebookBot.startWebHook(true)
}