package io.kommons.designpatterns.balking

import java.time.Duration

/**
 * DelayProvider
 */
interface DelayProvider {

    suspend fun executeAfterDelay(delayDuration: Duration, task: suspend () -> Unit)

}