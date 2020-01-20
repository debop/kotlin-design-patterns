package io.kommons.testcontainers.message

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.Arrays

@Disabled("현재로서는 Rabbitmq를 사용하지 않는다.")
class RabbitmqServerTest {

    companion object: KLogging() {
        const val RABBITMQ_TEST_EXCHANGE = "TestExchange"
        const val RABBITMQ_TEST_ROUTING_KEY = "TestRoutingKey"
        const val RABBITMQ_TEST_MESSAGE = "Hello world"
    }

    private lateinit var rabbitmqServer: RabbitmqServer

    @BeforeAll
    fun setup() {
        rabbitmqServer = RabbitmqServer()
        rabbitmqServer.start()
    }

    @AfterAll
    fun cleanup() {
        rabbitmqServer.close()
    }

    @Test
    fun `run rabbitmq server`() {
        rabbitmqServer.isRunning.shouldBeTrue()
        log.debug { "host=${rabbitmqServer.host}" }
        log.debug { "port=${rabbitmqServer.port}" }
    }

    @Test
    fun `run rabbitmq server with default port`() {
        RabbitmqServer(useDefaultPort = true)
            .apply { start() }
            .use { rabbitmq ->
                rabbitmq.isRunning.shouldBeTrue()
                log.debug { "url = ${rabbitmq.url}" }

                rabbitmq.port shouldEqualTo RabbitmqServer.RABBITMQ_PORT
            }
    }

    @Test
    fun `connect to rabbitmq server`() {
        val factory = ConnectionFactory().apply {
            host = rabbitmqServer.host
            port = rabbitmqServer.port
        }

        val connection = factory.newConnection()
        connection.shouldNotBeNull()

        val channel = connection.createChannel()
        channel.shouldNotBeNull()
        channel.exchangeDeclare(RABBITMQ_TEST_EXCHANGE, "direct", true)

        val queueName = channel.queueDeclare().queue
        channel.queueBind(queueName, RABBITMQ_TEST_EXCHANGE, RABBITMQ_TEST_ROUTING_KEY)

        // Set up a consumer on the queue
        var messageWasReceived = false
        channel.basicConsume(queueName, false, object: DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: BasicProperties?, body: ByteArray?) {
                messageWasReceived = Arrays.equals(body, RABBITMQ_TEST_MESSAGE.toByteArray())
            }
        })

        // post a message
        channel.basicPublish(RABBITMQ_TEST_EXCHANGE, RABBITMQ_TEST_ROUTING_KEY, null, RABBITMQ_TEST_MESSAGE.toByteArray())

        // check the message was received
        Thread.sleep(1000)
        messageWasReceived.shouldBeTrue()
    }
}