package io.kommons.testcontainers.aws

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement
import com.amazonaws.services.dynamodbv2.model.KeyType.HASH
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType.S
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class DynaliteServerTest {

    companion object: KLogging() {
        const val TABLE_NAME = "test_table"
        val region = Regions.AP_NORTHEAST_2
    }

    val dynaliteServer: DynaliteServer by lazy { DynaliteServer().apply { start() } }
    val client: AmazonDynamoDB by lazy { dynaliteServer.client }

    @Test
    fun `launch dynalite server`() {
        DynaliteServer()
            .apply { start() }
            .use {
                it.isRunning.shouldBeTrue()
            }
    }

    @Test
    fun `create table to dynalite server`() {
        client.shouldNotBeNull()

        val createTableRequest = CreateTableRequest()
            .withTableName(TABLE_NAME)
            .withAttributeDefinitions(AttributeDefinition("id", S))
            .withKeySchema(KeySchemaElement("id", HASH))
            .withProvisionedThroughput(ProvisionedThroughput(5L, 5L))

        val createTableResult = client.createTable(createTableRequest)
        createTableResult.shouldNotBeNull()
        log.debug { "Create table result=${createTableResult.tableDescription}" }

        // FIXME : 테이블은 제대로 생성되었는데, INSERT 시에 예외가 발생한다.
        //        val item = hashMapOf(
        //            "id" to AttributeValue().withS("1"),
        //            "name" to AttributeValue().withS("debop"),
        //            "age" to AttributeValue().withN("51"))
        //
        //        val result = client.putItem(PutItemRequest().withTableName(TABLE_NAME).withItem(item))
        //        result.shouldNotBeNull()
        //
        //        val search = client.scan(ScanRequest(TABLE_NAME))
        //        search.shouldNotBeNull()
        //        search.count shouldEqualTo 1
    }
}