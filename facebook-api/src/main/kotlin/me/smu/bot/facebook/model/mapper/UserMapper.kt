package me.smu.bot.facebook.model.mapper

import me.smu.bot.facebook.model.data.User
import me.smu.bot.facebook.model.network.client.api.data.Recipient

fun User.toRecipient(): Recipient {
    return Recipient(id = id)
}