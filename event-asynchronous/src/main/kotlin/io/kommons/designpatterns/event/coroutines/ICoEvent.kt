package io.kommons.designpatterns.event.coroutines

/**
 * ICoEvent
 *
 * @author debop
 */
interface ICoEvent {

    suspend fun start()

    suspend fun stop()

    suspend fun status()
}