package io.kommons.designpatterns.abstractfactory.orc

import io.kommons.designpatterns.abstractfactory.Army
import io.kommons.designpatterns.abstractfactory.Castle
import io.kommons.designpatterns.abstractfactory.King
import io.kommons.designpatterns.abstractfactory.KingdomFactory

/**
 * OrcKingdomFactory
 *
 * @author debop
 * @since 28/09/2019
 */
class OrcKingdomFactory: KingdomFactory {

    override fun createCastle(): Castle = OrcCastle()

    override fun createKing(): King = OrcKing()

    override fun createArmy(): Army = OrcArmy()
}