package io.kommons.designpatterns.abstractfactory.elf

import io.kommons.designpatterns.abstractfactory.King

/**
 * ElfKing
 *
 * @author debop
 * @since 28/09/2019
 */
class ElfKing: King {

    companion object {
        const val DESCRIPTION = "This is the Elven king!"
    }

    override fun getDescription(): String = DESCRIPTION

}