package io.kommons.designpatterns.servicelocator

/**
 * This is going to be the parent service interface which we will use to create our services. All
 * services will have a <ul><li>service name</li> <li>unique id</li> <li>execution work
 * flow</li></ul>
 *
 */
interface Service {

    /**
     * Unique ID of the particular service
     */
    val id: Int

    /**
     * The human readable name of the service
     */
    val name: String


    /*
     * The workflow method that defines what this service does
     */
    fun execute()
}