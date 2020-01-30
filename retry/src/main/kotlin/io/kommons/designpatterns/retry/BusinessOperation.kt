package io.kommons.designpatterns.retry

@FunctionalInterface
interface BusinessOperation<T> {

    @Throws(BusinessException::class)
    fun perform(): T
}

fun <T> businessOperationOf(action: () -> T): BusinessOperation<T> =
    object: BusinessOperation<T> {
        override fun perform(): T {
            return action.invoke()
        }
    }