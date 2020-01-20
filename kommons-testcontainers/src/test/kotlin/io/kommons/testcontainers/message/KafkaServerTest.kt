package io.kommons.testcontainers.message

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldBeGreaterOrEqualTo
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.UUID

class KafkaServerTest {

    companion object: KLogging() {
        const val TOPIC_NAME = "test-containers-topic.1"

        val kafkaServer = KafkaServer().apply { start() }

        fun createProducer(): KafkaProducer<String, String> {
            val map = mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaServer.bootstrapServers,
                ProducerConfig.CLIENT_ID_CONFIG to UUID.randomUUID().toString()
            )

            return KafkaProducer<String, String>(map, StringSerializer(), StringSerializer())
        }

        fun createConsumer(): KafkaConsumer<String, String> {
            val map = mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaServer.bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG to "tc-" + UUID.randomUUID().toString(),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
            )

            return KafkaConsumer<String, String>(map, StringDeserializer(), StringDeserializer())
        }
    }

    @Test
    fun `run kafka server`() {
        kafkaServer.shouldNotBeNull()

        log.debug { "bootstrapServers=${kafkaServer.bootstrapServers}" }
        log.debug { "boundPortNumbers=${kafkaServer.boundPortNumbers}" }

        kafkaServer.bootstrapServers.shouldNotBeEmpty()
    }

    @Test
    fun `producing and consuming message`() {

        val consumer = createConsumer()
        consumer.subscribe(listOf(TOPIC_NAME))

        val producer = createProducer()
        producer.shouldNotBeNull()

        val producedResult = producer.send(ProducerRecord(TOPIC_NAME, "message-key", "message-value"))

        val producedRecord = producedResult.get()
        log.debug { "producedRecord=$producedRecord" }
        producedRecord.toString() shouldContain TOPIC_NAME

        val consumerRecords = consumer.poll(Duration.ofMillis(3000))

        consumerRecords.shouldNotBeEmpty()
        consumerRecords.count() shouldBeGreaterOrEqualTo 1
        val consumerRecord = consumerRecords.first()
        consumerRecord.key() shouldEqual "message-key"
        consumerRecord.value() shouldEqual "message-value"

        producer.close()

        consumer.unsubscribe()
        consumer.close()
    }
}