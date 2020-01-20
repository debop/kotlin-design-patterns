package io.kommons

/**
 * Singleton 객체를 보관해주는 객체입니다.
 *
 * ```kotlin
 *
 * class Manager private constructor(private val context:Context) {
 *
 *     companion object: SingletonHolder<Manager> { Manager(context) }
 *
 *     fun doSutff() {}
 * }
 *
 * // Use singleton
 * val manager = Manager.getInstance()
 * manager.doStuff()
 * ````
 *
 * @author debop
 */
open class SingletonHolder<out R>(factory: () -> R) {

    private var factory: (() -> R)? = factory

    @Volatile
    private var instance: R? = null

    fun getInstance(): R {
        val result: R? = instance

        return result ?: synchronized(this) {
            val result2: R? = instance
            result2 ?: run {
                val created = factory?.invoke()
                instance = created
                factory = null
                created!!
            }
        }
    }
}