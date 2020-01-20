package io.kommons.designpatterns.ambassador

/**
 * Interface shared by [RemoteService] and [ServiceEmbassador]
 *
 * @author debop
 */
interface RemoteServiceInterface {

    companion object {
        const val FAILURE = -1L
    }

    fun doRemoteFunction(value: Int): Long
}