package io.kommons.designpatterns.databus

/**
 * AbstractDataType
 *
 * @author debop
 */
abstract class AbstractDataType(override var dataBus: DataBus = DataBus.getInstance()): DataType