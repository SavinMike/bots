package me.ivmg.telegram.dispatcher.handlers

import me.ivmg.telegram.Bot
import me.ivmg.telegram.ContactHandleUpdate
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.Update

class ContactHandler(handleUpdate: ContactHandleUpdate) : Handler(ContactHandleUpdateProxy(handleUpdate)) {

    override val groupIdentifier: String
        get() = "System"

    override fun checkUpdate(update: Update): Boolean {
        return update.message?.contact != null
    }
}

private class ContactHandleUpdateProxy(private val handleUpdate: ContactHandleUpdate) : HandleUpdate {
    override fun invoke(p1: Bot, p2: Update): Boolean {
        return handleUpdate(p1, p2, p2.message?.contact!!)
    }
}