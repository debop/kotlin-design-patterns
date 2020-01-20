package io.kommons

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod


val Class<*>.packageName: String get() = this.`package`.name
val KClass<*>.packageName: String get() = this.java.packageName

val KFunction<*>.qualifiedName: String get() = this.javaMethod?.declaringClass?.name + name

/**
 * 객체를 지정한 수형으로 casting 합니다.
 */
@Suppress("UNCHECKED_CAST")
fun <T: Any> Any.cast(kclass: KClass<T>): T =
    if (kclass.java.isInstance(this)) this as T
    else throw ClassCastException("${this::class} couldn't be cast to $kclass")

/**
 * 객체를 지정한 수형으로 casting 합니다.
 */
inline fun <reified T: Any> Any.cast(): T = cast(T::class)

/**
 * 지정한 수형의 인스턴스를 새로 생성합니다. 실패 시에는 null을 반환합니다.
 */
fun <T: Any> Class<T>.newInstanceOrNull(): T? = runCatching { newInstance() }.getOrNull()

/**
 * 지정한 수형의 인스턴스를 새로 생성합니다. 실패 시에는 null을 반환합니다.
 */
fun <T: Any> KClass<T>.newInstanceOrNull(): T? = runCatching { java.newInstance() }.getOrNull()

/**
 * 지정한 수형의 인스턴스를 새로 생성합니다. 실패 시에는 null을 반환합니다.
 */
@Suppress("UNCHECKED_CAST")
fun <T: Any> newInstanceOrNull(qualifiedName: String, classLoader: ClassLoader? = getContextClassLoader()): T? {
    qualifiedName.assertNotBlank("qualifiedName")

    return runCatching {
        val clazz = (classLoader?.loadClass(qualifiedName) ?: Class.forName(qualifiedName)) as? Class<T>
        clazz?.run { newInstanceOrNull() }
    }.getOrNull()
}


/**
 * 지정한 클래스명에 해당하는 라이브러리가 Classpath 상에 존재하는지 검사합니다.
 *
 * @param qualifiedName 찾고자 하는 class name (ex: java.lang.String)
 * @param classLoader 현재 class loader
 */
@JvmOverloads
fun classIsPresent(qualifiedName: String, classLoader: ClassLoader? = Thread.currentThread().contextClassLoader): Boolean {
    return try {
        classLoader?.loadClass(qualifiedName) ?: Class.forName(qualifiedName) != null
    } catch (ignored: Throwable) {
        false
    }
}

/**
 * 지정한 수형의 모든 상위 수형을 찾습니다.
 */
fun Class<*>.findAllSuperTypes(): List<Class<*>> {
    val result = LinkedHashSet<Class<*>>()
    findAllSuperTypes(mutableListOf(this to supertypes()), mutableSetOf(this), result)
    return result.toList()
}

private tailrec fun findAllSuperTypes(nodes: MutableList<Pair<Class<*>, MutableList<Class<*>>>>,
                                      path: MutableSet<Class<*>>,
                                      visited: MutableSet<Class<*>>) {
    if (nodes.isEmpty()) {
        return
    }

    val (current, children) = nodes[nodes.lastIndex]

    if (children.isEmpty()) {
        visited.add(current)
        path.remove(current)
        nodes.removeLast()
    } else {
        val next = children.removeLast()
        next?.let {
            if (path.add(it)) {
                nodes.add(it to it.supertypes())
            }
        }
    }
    findAllSuperTypes(nodes, path, visited)
}

private fun Class<*>.supertypes(): MutableList<Class<*>> = when {
    superclass == null         -> interfaces?.toMutableList() ?: mutableListOf()
    interfaces.isNullOrEmpty() -> mutableListOf(superclass)
    else                       -> ArrayList<Class<*>>(interfaces.size + 1).also {
        interfaces.toCollection(it)
        it.add(superclass)
    }
}

private fun <T> MutableList<T>.removeLast(): T? =
    if (isNotEmpty()) {
        removeAt(lastIndex)
    } else null
