package me.smu.bot.model.router.screen.stack

import me.smu.bot.model.router.screen.Screen
import me.smu.bot.model.sope.Scope
import kotlin.reflect.KClass

/**
 * Provide behaviour how screen should be added to stackManager
 *
 * @see ScreenStackManager
 */
interface ScreenStrategy {
    fun apply(screen: Screen, currentCommandsList: MutableList<Screen>)
}

/**
 * Provide behaviour how screen should be removed from stackManager
 *
 * @see ScreenStackManager
 */
interface BackScreenStrategy {
    fun apply(currentCommandsList: MutableList<Screen>)
}

object AddToEndScreenStrategy : ScreenStrategy {
    override fun apply(screen: Screen, currentCommandsList: MutableList<Screen>) {
        currentCommandsList.add(screen)
    }
}

object ReplaceScreenStrategy : ScreenStrategy {
    override fun apply(screen: Screen, currentCommandsList: MutableList<Screen>) {
        currentCommandsList.removeAt(currentCommandsList.lastIndex)
        currentCommandsList.add(screen)
    }
}

object SingleTopScreenStrategy : ScreenStrategy {
    override fun apply(screen: Screen, currentCommandsList: MutableList<Screen>) {
        currentCommandsList.removeIf {
            it::class == screen::class
        }

        currentCommandsList.add(screen)
    }
}

object SkipScreenStrategy : ScreenStrategy {
    override fun apply(screen: Screen, currentCommandsList: MutableList<Screen>) {
        //skip
    }
}

object ClearScreenStrategy : ScreenStrategy {
    override fun apply(screen: Screen, currentCommandsList: MutableList<Screen>) {
        currentCommandsList.clear()
        currentCommandsList.add(screen)
    }
}

//Predefined BackScreenStrategy

class ClearScopeScreenStrategy<T: Scope>(private val scope: Class<T>) : BackScreenStrategy {

    override fun apply(currentCommandsList: MutableList<Screen>) {
        currentCommandsList.removeIf {
            scope.isAssignableFrom(it.scope::class.java)
        }
    }
}

object SkipBackScreenStrategy : BackScreenStrategy {
    override fun apply(currentCommandsList: MutableList<Screen>) {
        //skip
    }

}

object RemoveCurrentScreenStrategy : BackScreenStrategy {
    override fun apply(currentCommandsList: MutableList<Screen>) {
        val index = currentCommandsList.lastIndex
        if (index >= 0) {
            currentCommandsList.removeAt(index)
        }
    }
}

class MultiScreenStrategy(vararg backScreenStrategy: BackScreenStrategy) : BackScreenStrategy {
    private val strategy = mutableListOf<BackScreenStrategy>()

    override fun apply(currentCommandsList: MutableList<Screen>) {
        strategy.forEach {
            it.apply(currentCommandsList)
        }
    }

    init {
        strategy.addAll(backScreenStrategy)
    }
}