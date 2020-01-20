package io.kommons.junit.jupiter.output

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.slf4j.LoggerFactory
import org.slf4j.helpers.SubstituteLogger
import java.util.LinkedList
import kotlin.reflect.KClass
import ch.qos.logback.classic.Logger as LogbackLogger

/**
 * 로그 메시지를 메모리에 캡쳐하기 위한 Logback Appender입니다.
 * NOTE: 단 parallel 테스트 시에는 제대로 Logger를 casting 할 수 없습니다.
 * HINT : http://www.slf4j.org/codes.html#substituteLogger
 *
 * @author debop
 */
class InMemoryAppender(name: String = "root"): AppenderBase<ILoggingEvent>() {

    constructor(clazz: Class<*>): this(clazz.name)
    constructor(kclass: KClass<*>): this(kclass.qualifiedName!!)

    /**
     * Logging 시스템 초기화 전에 생성하면 다른 Logger가 생성됩니다.
     */
    private val logger by lazy {
        // Slf4j가 초기화될때까지 기다린다
        do {
            Thread.sleep(1)
            val logger = LoggerFactory.getLogger(name)

        } while (logger is SubstituteLogger)

        LoggerFactory.getLogger(name) as LogbackLogger
    }

    private val events = LinkedList<ILoggingEvent>()

    val size: Int get() = events.size
    val lastMessage: String? get() = events.lastOrNull()?.message
    val messages: List<String> get() = events.map { it.message }

    init {
        start()
        logger.addAppender(this)
    }

    override fun append(eventObject: ILoggingEvent?) {
        eventObject?.run { events.add(this) }
    }

    override fun stop() {
        logger.detachAppender(this)
        clear()
        super.stop()
    }

    fun clear() {
        events.clear()
    }

}