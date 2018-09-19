package me.smu.bot.facebook.model.data.callback

import me.smu.bot.facebook.model.data.User
import me.smu.bot.facebook.model.data.Watermark

class MessageReadsEvent(override val sender: User,
                        override val recipient: User,
                        override val timestamp: Long,
                        val read: Watermark) : WebhookEvent
