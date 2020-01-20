package io.kommons.testcontainers.aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement
import com.amazonaws.services.dynamodbv2.model.KeyType.HASH
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType.S
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class DynamoDBServerTest {

    companion object: KLogging() {
        const val TABLE_NAME = "test_table"
        val region = Regions.AP_NORTHEAST_2
    }

    val client: AmazonDynamoDB by lazy {
        AmazonDynamoDBClientBuilder
            .standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(dynamodb.url, region.toString()))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials("accessKey", "secretKey")))
            .build()
    }

    lateinit var dynamodb: DynamoDBServer

    @BeforeAll
    fun setup() {
        dynamodb = DynamoDBServer().apply { start() }

        client.shouldNotBeNull()

        val createTableRequest = CreateTableRequest()
            .withTableName(TABLE_NAME)
            .withAttributeDefinitions(AttributeDefinition("id", S))
            .withKeySchema(KeySchemaElement("id", HASH))
            .withProvisionedThroughput(ProvisionedThroughput(5L, 5L))

        val createTableResult = client.createTable(createTableRequest)
        createTableResult.shouldNotBeNull()
        log.debug { "Create table result=${createTableResult.tableDescription}" }
    }

    @AfterAll
    fun cleanup() {
        dynamodb.close()
    }

    @Test
    fun `insert some data`() {
        val item = hashMapOf(
            "id" to AttributeValue().withS("1"),
            "name" to AttributeValue().withS("debop"),
            "age" to AttributeValue().withN("51"))

        val result = client.putItem(PutItemRequest().withTableName(TABLE_NAME).withItem(item))
        result.shouldNotBeNull()

        val search = client.scan(ScanRequest(TABLE_NAME))
        search.shouldNotBeNull()
        search.count shouldEqualTo 1
    }
}