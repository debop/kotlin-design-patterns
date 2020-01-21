package io.kommons.designpatterns.throttling

/**
 * Tenant
 *
 * @author debop
 */
data class Tenant(val name: String,
                  val allowedCallsPerSecond: Long,
                  private val callsCount: CallsCount) {
    init {
        require(allowedCallsPerSecond >= 0L) { "Number of calls less than 0 not allowed." }
        callsCount.addTenant(name)
    }
}