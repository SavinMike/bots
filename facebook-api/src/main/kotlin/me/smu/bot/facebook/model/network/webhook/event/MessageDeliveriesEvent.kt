package me.smu.bot.facebook.model.network.webhook.event

import me.smu.bot.facebook.model.data.User
import me.smu.bot.facebook.model.data.Watermark

/**
 * This callback will occur when a message a Page has sent has been delivered.
 */
data class MessageDeliveriesEvent(override val sender: User,
                                  override val recipient: User,
                                  override val timestamp: Long,
                                  val delivery: Watermark) : WebhookEvent