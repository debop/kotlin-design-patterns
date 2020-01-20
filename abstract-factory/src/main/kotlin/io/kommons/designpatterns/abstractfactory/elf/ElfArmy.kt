package io.kommons.designpatterns.abstractfactory.elf

import io.kommons.designpatterns.abstractfactory.Army

/**
 * ElfArmy
 *
 * @author debop
 * @since 28/09/2019
 */
class ElfArmy: Army {

    companion object {
        const val DESCRIPTION = "This is the Elven Army!"
    }

    override fun getDescription(): String = DESCRIPTION

}