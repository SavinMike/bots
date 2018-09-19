package me.ivmg.telegram.dispatcher.handlers

import me.ivmg.telegram.Bot
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.LocationHandleUpdate
import me.ivmg.telegram.entities.Update

class LocationHandler(handleUpdate: LocationHandleUpdate) : Handler(LocationHandleUpdateProxy(handleUpdate)) {
    override val groupIdentifier: String
        get() = "System"

    override fun checkUpdate(update: Update): Boolean {
        return update.message?.location != null
    }
}

private class LocationHandleUpdateProxy(private val handleUpdate: LocationHandleUpdate) : HandleUpdate {
    override fun invoke(p1: Bot, p2: Update): Boolean {
        return handleUpdate(p1, p2, p2.message?.location!!)
    }
}