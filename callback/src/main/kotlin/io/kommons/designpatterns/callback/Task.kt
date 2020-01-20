package io.kommons.designpatterns.callback

import io.kommons.logging.KLogging
import io.kommons.logging.debug

abstract class Task {

    fun executeWithCallback(callback: Callback? = null) {
        execute()
        callback?.run {
            call()
        }
    }

    fun executeWithLambda(callback: () -> Unit) {
        execute()
        callback.invoke()
    }

    abstract fun execute()
}

class SimpleTask: Task() {

    companion object: KLogging()

    override fun execute() {
        log.debug { "실제 작업을 수행합니다. 이후에 callback이 실행될 것입니다." }
    }
}