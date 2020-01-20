package io.kommons.designpatterns.abstractfactory

import io.kommons.designpatterns.abstractfactory.FactoryMaker.KingdomType
import io.kommons.designpatterns.abstractfactory.elf.ElfArmy
import io.kommons.designpatterns.abstractfactory.elf.ElfCastle
import io.kommons.designpatterns.abstractfactory.elf.ElfKing
import io.kommons.designpatterns.abstractfactory.orc.OrcArmy
import io.kommons.designpatterns.abstractfactory.orc.OrcCastle
import io.kommons.designpatterns.abstractfactory.orc.OrcKing
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class AbstractFactoryTest {

    private val elfFactory = FactoryMaker.makeFactory(KingdomType.ELF)
    private val orcFactory = FactoryMaker.makeFactory(KingdomType.ORC)

    @Test
    fun `create King`() {
        val elfKing = elfFactory.createKing()
        elfKing shouldBeInstanceOf ElfKing::class

        val orcKing = orcFactory.createKing()
        orcKing shouldBeInstanceOf OrcKing::class
    }

    @Test
    fun `create Castle`() {
        val elfCastle = elfFactory.createCastle()
        elfCastle shouldBeInstanceOf ElfCastle::class

        val orcCastle = orcFactory.createCastle()
        orcCastle shouldBeInstanceOf OrcCastle::class
    }

    @Test
    fun `create Army`() {
        val elfArmy = elfFactory.createArmy()
        elfArmy shouldBeInstanceOf ElfArmy::class

        val orcArmy = orcFactory.createArmy()
        orcArmy shouldBeInstanceOf OrcArmy::class
    }

    @Test
    fun `create ElfKingdom`() {
        with(elfFactory) {
            val king = createKing()
            val castle = createCastle()
            val army = createArmy()

            king shouldBeInstanceOf ElfKing::class
            king.getDescription() shouldEqual ElfKing.DESCRIPTION

            castle shouldBeInstanceOf ElfCastle::class
            castle.getDescription() shouldEqual ElfCastle.DESCRIPTION

            army shouldBeInstanceOf ElfArmy::class
            army.getDescription() shouldEqual ElfArmy.DESCRIPTION
        }
    }

    @Test
    fun `create OrcKingdom`() {
        with(orcFactory) {
            val king = createKing()
            val castle = createCastle()
            val army = createArmy()

            king shouldBeInstanceOf OrcKing::class
            king.getDescription() shouldEqual OrcKing.DESCRIPTION

            castle shouldBeInstanceOf OrcCastle::class
            castle.getDescription() shouldEqual OrcCastle.DESCRIPTION

            army shouldBeInstanceOf OrcArmy::class
            army.getDescription() shouldEqual OrcArmy.DESCRIPTION
        }
    }
}