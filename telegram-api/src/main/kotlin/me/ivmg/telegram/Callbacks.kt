package me.ivmg.telegram

import me.ivmg.telegram.entities.Contact
import me.ivmg.telegram.entities.Location
import me.ivmg.telegram.entities.Update
import me.ivmg.telegram.errors.TelegramError

typealias HandleUpdate = (Bot, Update) -> Boolean

typealias HandleError = (Bot, TelegramError) -> Unit

typealias CommandHandleUpdate = (Bot, Update, List<String>) -> Boolean

typealias ContactHandleUpdate = (Bot, Update, Contact) -> Boolean

typealias LocationHandleUpdate = (Bot, Update, Location) -> Boolean
