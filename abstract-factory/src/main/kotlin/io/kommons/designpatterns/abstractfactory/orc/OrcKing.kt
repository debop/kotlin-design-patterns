package io.kommons.designpatterns.abstractfactory.orc

import io.kommons.designpatterns.abstractfactory.King

/**
 * OrcKing
 *
 * @author debop
 * @since 28/09/2019
 */
class OrcKing: King {

    companion object {
        const val DESCRIPTION = "This is the Orc King!"
    }

    override fun getDescription(): String = DESCRIPTION

}