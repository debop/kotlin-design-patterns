package io.kommons.designpatterns.singleton

/**
 * Thread-safe Singleton class. The instance is lazily initialized and thus needs synchronization
 * mechanism.
 *
 * Note: if created by reflection then a singleton will not be created but multiple options in the same classloader
 */
class ThreadSafeLazyLoadedIvoryTower private constructor() {

    init {
        // protect against instantiation via reflection
        if (instance == null) {
            instance = this
        } else {
            error("Already initialized.")
        }
    }

    companion object {
        private var instance: ThreadSafeLazyLoadedIvoryTower? = null

        /**
         * The instance gets created only when it is called for first time. Lazy-loading
         */
        @Synchronized
        fun getInstance(): ThreadSafeLazyLoadedIvoryTower {
            if (instance == null) {
                instance = ThreadSafeLazyLoadedIvoryTower()
            }
            return instance!!
        }
    }
}