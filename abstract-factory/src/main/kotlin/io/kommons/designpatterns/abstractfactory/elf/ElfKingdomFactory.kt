package io.kommons.designpatterns.abstractfactory.elf

import io.kommons.designpatterns.abstractfactory.Army
import io.kommons.designpatterns.abstractfactory.Castle
import io.kommons.designpatterns.abstractfactory.King
import io.kommons.designpatterns.abstractfactory.KingdomFactory

/**
 * ElfKingdomFactory
 *
 * @author debop
 * @since 28/09/2019
 */
class ElfKingdomFactory: KingdomFactory {

    override fun createCastle(): Castle = ElfCastle()

    override fun createKing(): King = ElfKing()

    override fun createArmy(): Army = ElfArmy()
}