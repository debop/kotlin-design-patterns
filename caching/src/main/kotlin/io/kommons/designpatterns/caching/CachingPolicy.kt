package io.kommons.designpatterns.caching

/**
 * CachingPolicy
 *
 * @author debop
 * @since 24/09/2019
 */
enum class CachingPolicy(val policy: String) {

    THROUGH("through"),
    AROUND("around"),
    BEHIND("behind"),
    ASIDE("aside")

}