package me.smu.bot.facebook.model.data.callback

import me.smu.bot.facebook.model.data.GamePlay
import me.smu.bot.facebook.model.data.User

/**
 * This callback occurs after a person played a round of Instant Games.
 */
data class MessagingGamePlaysEvent(override val sender: User,
                                   override val recipient: User,
                                   override val timestamp: Long,
                                   val gamePlay: GamePlay): WebhookEvent