package io.kommons

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import java.io.Serializable
import java.util.UUID

/**
 * Thread context ㅂㅕㄹ로 Local storage를 제공하는 object 입니다.
 *
 * @author debop
 */
@Suppress("UNCHECKED_CAST")
object Local: KLogging() {

    private val threadLocal: ThreadLocal<java.util.HashMap<Any, Any?>> by lazy {
        object: ThreadLocal<HashMap<Any, Any?>>() {
            override fun initialValue(): HashMap<Any, Any?> {
                return HashMap()
            }
        }
    }

    @JvmStatic
    val storage: HashMap<Any, Any?>
        get() = threadLocal.get()

    @JvmStatic
    fun save(): HashMap<Any, Any?> = storage.clone() as HashMap<Any, Any?>

    @JvmStatic
    fun restore(saved: HashMap<Any, Any?>) {
        threadLocal.set(saved)
    }

    @JvmStatic
    operator fun <T: Any> get(key: Any): T? = storage[key] as? T

    @JvmStatic
    operator fun <T: Any> set(key: Any, value: T?) {
        when (value) {
            null -> storage.remove(key)
            else -> storage[key] = value
        }
    }

    /**
     * Thread Local 저장소의 모든 저장 데이터를 삭제합니다.
     */
    @JvmStatic
    fun clearAll() {
        log.debug { "Clear local storage." }
        storage.clear()
    }

    /**
     * Thread Local 저장소의 데이터를 가져옵니다. 없다면 새로 생성하여 저장한 후 반환합니다.
     */
    @JvmStatic
    inline fun <T: Any> getOrPut(key: Any, defaultValue: () -> T?): T? {
        return storage.getOrPut(key, defaultValue) as? T
    }
}

@Suppress("UNCHECKED_CAST")
class LocalStorage<T: Any>: Serializable {

    private val key: UUID = UUID.randomUUID()

    fun get(): T? = Local[key]

    fun set(value: T?) {
        Local[key] = value
    }

    fun update(value: T?) {
        set(value)
    }

    fun clear(): T? {
        return Local.storage.remove(key) as? T
    }
}