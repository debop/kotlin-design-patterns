package io.kommons.designpatterns.abstractfactory.orc

import io.kommons.designpatterns.abstractfactory.Castle

/**
 * OrcCastle
 *
 * @author debop
 * @since 28/09/2019
 */
class OrcCastle: Castle {

    companion object {
        const val DESCRIPTION = "This is the Orc Castle!"
    }

    override fun getDescription(): String = DESCRIPTION

}