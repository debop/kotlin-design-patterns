package io.kommons.designpatterns.methodinvocation.async

/**
 * AsyncCallback interface
 */
@FunctionalInterface
interface AsyncCallback<T> {

    /**
     * Complete handler which is executed when async task is completed or fails execution.
     *
     * @param value the evaluated value from async task, undefined when execution fails
     * @param ex empty value if execution succeeds, some exception if executions fails
     */
    fun onComplete(value: T?, ex: Throwable?)
}

fun <T> asyncCallback(onCompleteHandler: (value: T?, ex: Throwable?) -> Unit) =
    object: AsyncCallback<T> {
        override fun onComplete(value: T?, ex: Throwable?) {
            onCompleteHandler(value, ex)
        }
    }
