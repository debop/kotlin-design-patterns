package io.kommons.testcontainers.message

import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.apache.pulsar.client.api.PulsarClient
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

@Disabled("Pulsar를 사용할 일이 없다 ㅠ.ㅠ")
class PulsarServerTest {

    companion object: KLogging() {
        const val TEST_TOPIC = "test_topic"
    }

    @Test
    fun `create pulsar server`() {
        PulsarServer().use { pulsar ->
            pulsar.start()
            pulsar.isRunning.shouldBeTrue()
            testPulsarFunctionality(pulsar.pulsarBrokerUrl)
        }
    }

    @Disabled("테스트 완료")
    @Test
    fun `create pulsar server with default port`() {
        PulsarServer(useDefaultPort = true).use { pulsar ->
            pulsar.start()
            pulsar.isRunning.shouldBeTrue()
            testPulsarFunctionality(pulsar.pulsarBrokerUrl)
        }
    }

    private fun testPulsarFunctionality(pulsarBlockerUrl: String) {
        PulsarClient.builder()
            .serviceUrl(pulsarBlockerUrl)
            .build()
            .use { client ->
                client.newConsumer()
                    .topic(TEST_TOPIC)
                    .subscriptionName("test-subs")
                    .subscribe()
                    .use { consumer ->
                        client.newProducer()
                            .topic(TEST_TOPIC)
                            .create()
                            .use { producer ->
                                producer.send("test containers".toByteArray())

                                val future = consumer.receiveAsync()
                                val message = future.get(5, TimeUnit.SECONDS)

                                message.data.toString(Charsets.UTF_8) shouldEqual "test containers"
                            }
                    }
            }
    }
}