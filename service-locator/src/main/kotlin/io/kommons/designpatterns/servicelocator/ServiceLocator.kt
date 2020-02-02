package io.kommons.designpatterns.servicelocator

/**
 * The service locator module.
 * Will fetch service from cache, otherwise creates a fresh service and update cache
 */
object ServiceLocator {

    private val serviceCache = ServiceCache()

    /**
     * Fetch the service with the name param from the cache first, if no service is found, lookup the
     * service from the {@link InitContext} and then add the newly created service into the cache map
     * for future requests.
     *
     * @param serviceJndiName a string
     * @return [Service]
     */
    fun getService(serviceJndiName: String): Service? {
        return serviceCache.getService(serviceJndiName)
               ?: run {
                   /*
                    * If we are unable to retrive anything from cache, then lookup the service
                    * and add it in the cache map
                    */
                   InitContext().lookup(serviceJndiName)?.also {
                       serviceCache.addService(it as Service)
                   } as? Service
               }
    }
}