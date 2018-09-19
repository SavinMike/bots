package me.smu.bot.model.bus

import me.smu.bot.model.bus.event.Event
import java.util.concurrent.ConcurrentLinkedQueue

object EventBus {

    private val eventEmitterList = ConcurrentLinkedQueue<EventEmitter>()

    fun addEventEmitter(eventEmitter: EventEmitter) {
        eventEmitterList.add(eventEmitter)
    }

    fun removeEventEmitter(eventEmitter: EventEmitter) {
        eventEmitterList.remove(eventEmitter)
    }

    fun sendEvent(event: Event) {
        eventEmitterList.forEach { it(event) }
    }
}

typealias EventEmitter = (Event) -> Unit