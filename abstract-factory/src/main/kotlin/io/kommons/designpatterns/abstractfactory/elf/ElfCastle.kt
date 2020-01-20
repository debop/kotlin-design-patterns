package io.kommons.designpatterns.abstractfactory.elf

import io.kommons.designpatterns.abstractfactory.Castle

/**
 * ElfCastle
 *
 * @author debop
 * @since 28/09/2019
 */
class ElfCastle: Castle {

    companion object {
        const val DESCRIPTION = "This is the Elven castle!"
    }

    override fun getDescription(): String = DESCRIPTION

}