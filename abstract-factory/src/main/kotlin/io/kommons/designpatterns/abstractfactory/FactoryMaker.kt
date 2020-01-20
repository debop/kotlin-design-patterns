package io.kommons.designpatterns.abstractfactory

import io.kommons.designpatterns.abstractfactory.elf.ElfKingdomFactory
import io.kommons.designpatterns.abstractfactory.orc.OrcKingdomFactory

/**
 * FactoryMaker
 *
 * @author debop
 * @since 28/09/2019
 */
object FactoryMaker {

    enum class KingdomType {
        ELF, ORC
    }

    fun makeFactory(type: KingdomType): KingdomFactory = when (type) {
        KingdomType.ELF -> ElfKingdomFactory()
        KingdomType.ORC -> OrcKingdomFactory()
    }
}