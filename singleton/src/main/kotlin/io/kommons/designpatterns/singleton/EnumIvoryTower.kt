package io.kommons.designpatterns.singleton

/**
 * Enum based singleton implementation. Effective Java 2nd Edition (Joshua Bloch) p. 18
 *
 * This implementation is thread safe, however adding any other method and its thread safety
 * is developers responsibility.
 */
enum class EnumIvoryTower {

    INSTANCE;

    override fun toString(): String = javaClass.canonicalName + "@" + hashCode()

}