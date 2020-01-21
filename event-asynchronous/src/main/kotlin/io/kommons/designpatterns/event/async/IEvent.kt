package io.kommons.designpatterns.event.async

/**
 * Events that fulfill the start stop and list out current status behaviour follow this interface
 *
 * @author debop
 */
interface IEvent {

    val isSynchronous: Boolean

    fun start()

    fun stop()

    fun status()

}