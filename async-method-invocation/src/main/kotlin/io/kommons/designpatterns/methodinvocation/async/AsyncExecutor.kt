package io.kommons.designpatterns.methodinvocation.async

import java.util.concurrent.ExecutionException

/**
 * AsyncExecutor
 */
interface AsyncExecutor {

    /**
     * Starts processing of an async task. Returns immediately with async result. Executes callback
     * when the task is completed.
     *
     * @param task task to be executed asynchronously
     * @param callback callback to be executed on task completion
     * @return async result for the task
     */
    fun <T> startProcess(task: () -> T, callback: AsyncCallback<T>? = null): AsyncResult<T>

    /**
     * Ends processing of an async task. Blocks the current thread if necessary and returns the
     * evaluated value of the completed task.
     *
     * @param asyncResult async result of a task
     * @return evaluated value of the completed task
     * @throws ExecutionException if execution has failed, containing the root cause
     * @throws InterruptedException if the execution is interrupted
     */
    fun <T> endProcess(asyncResult: AsyncResult<T>): T
}