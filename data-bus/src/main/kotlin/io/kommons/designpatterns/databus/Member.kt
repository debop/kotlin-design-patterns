package io.kommons.designpatterns.databus

import java.util.function.Consumer

/**
 * Member
 *
 * @author debop
 */
interface Member: Consumer<DataType> {

    override fun accept(data: DataType)

}