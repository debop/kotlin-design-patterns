package io.kommons.designpatterns.strategy

import io.kommons.junit.jupiter.output.InMemoryAppender
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

/**
 * DragonSlayingStrategyTest
 *
 * @author debop
 */
@Execution(ExecutionMode.SAME_THREAD)
class DragonSlayingStrategyTest {

    companion object: KLogging()

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setup() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun cleanup() {
        appender.stop()
    }

    private fun dataProvider() = listOf(
        Arguments.of(MeleeStrategy(), "너의 엑스칼리버로 용의 머리를 잘라라!"),
        Arguments.of(ProjectileStrategy(), "당신이 마법의 석궁으로 용을 쏘면 용은 땅에 쓰러져 죽는다."),
        Arguments.of(SpellStrategy(), "당신은 파괴의 주문을 하고, 용은 먼지 더미 속으로 증발해버린다!")
    )

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun `execute strategy`(strategy: DragonSlayingStrategy, expectedResult: String) {
        strategy.execute()
        appender.lastMessage shouldEqual expectedResult
    }
}