package io.kommons.designpatterns.databus

/**
 * Events are sent via the Data-Bus
 *
 * @author debop
 */
interface DataType {

    var dataBus: DataBus

}