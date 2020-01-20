package io.kommons.designpatterns.abstractfactory.orc

import io.kommons.designpatterns.abstractfactory.Army

/**
 * OrcArmy
 *
 * @author debop
 * @since 28/09/2019
 */
class OrcArmy: Army {

    companion object {
        const val DESCRIPTION = "This is the Orc Army!"
    }

    override fun getDescription(): String = DESCRIPTION

}