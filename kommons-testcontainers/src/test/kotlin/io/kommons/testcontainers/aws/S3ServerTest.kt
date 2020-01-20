package io.kommons.testcontainers.aws

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions.AP_NORTHEAST_2
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import io.kommons.logging.KLogging
import io.kommons.logging.info
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test

class S3ServerTest {

    companion object: KLogging() {
        val bucketName = S3Server.DEFAULT_BUCKET_NAME
        val s3Server = S3Server(bucketName = bucketName).apply { start() }
    }

    private fun endPointConfiguration() =
        AwsClientBuilder.EndpointConfiguration(s3Server.url, AP_NORTHEAST_2.name)

    private fun createS3(): AmazonS3 =
        AmazonS3ClientBuilder
            .standard()
            .withEndpointConfiguration(endPointConfiguration())
            .withPathStyleAccessEnabled(true)
            .build()

    @Test
    fun `launch S3 Server`() {
        s3Server.shouldNotBeNull()
        s3Server.isRunning.shouldBeTrue()
        s3Server.host shouldEqual "localhost"
    }

    @Test
    fun `connect to S3 Server`() {
        val s3 = createS3()

        val list = s3.listObjects(bucketName, "1/2/3")
        list.objectSummaries.size shouldEqualTo 0
    }

    @Test
    fun `upload and download objects with S3`() {
        val s3 = createS3()
        s3.putObject(bucketName, "path1/path2/test01.txt", "test1234")
        s3.putObject(bucketName, "path1/path2/test02.txt", "test1234")
        s3.putObject(bucketName, "path1/path2/test03.txt", "test1234")

        val objectListing = s3.listObjects(bucketName)
        for (summary in objectListing.objectSummaries) {
            log.info { "Summary=$summary" }
        }
        objectListing.objectSummaries.size shouldEqualTo 3

        val s3Object = s3.getObject(bucketName, "path1/path2/test01.txt")
        s3Object.shouldNotBeNull()
        s3Object.objectContent.shouldNotBeNull()

        // TODO: kommons-io 를 제작한 후 추가하세요
        // s3Object.objectContent.toString(Charsets.UTF_8) shouldEqual "test1234"
    }
}