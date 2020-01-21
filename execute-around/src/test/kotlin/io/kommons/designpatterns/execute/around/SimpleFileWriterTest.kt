package io.kommons.designpatterns.execute.around

import io.kommons.junit.jupiter.folder.TempFolder
import io.kommons.junit.jupiter.folder.TempFolderExtension
import io.kommons.logging.KLogging
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File
import java.io.IOException
import java.nio.file.Files

/**
 * SimpleFileWriterTest
 */
@ExtendWith(TempFolderExtension::class)
class SimpleFileWriterTest {

    companion object: KLogging()

    private lateinit var tempFolder: TempFolder

    @BeforeEach
    fun beforeEach(tempFolder: TempFolder) {
        this.tempFolder = tempFolder
    }

    @Test
    fun `writer not null`() {
        val file = tempFolder.createFile("newFile")
        SimpleFileWriter(file.path) { writer ->
            writer.shouldNotBeNull()
        }
    }

    @Test
    fun `create non existing file`() {
        val file = File(tempFolder.root, "non-exsisting-file")
        file.exists().shouldBeFalse()

        SimpleFileWriter(file.path) { writer ->
            writer.shouldNotBeNull()
        }
        file.exists().shouldBeTrue()
    }

    @Test
    fun `write contents to file`() {
        val testMessage = "Test message"
        val file = tempFolder.createFile("testMessage")
        file.exists().shouldBeTrue()

        SimpleFileWriter(file.path) { writer ->
            writer.write(testMessage)
        }

        Files.lines(file.toPath()).allMatch { it == testMessage }.shouldBeTrue()
    }

    @Test
    fun `ripples IOException occurred while writing`() {
        val message = "Some error"

        assertThrows<IOException> {
            val file = tempFolder.createFile("error")
            SimpleFileWriter(file.path) { writer ->
                throw IOException(message)
            }
        }.localizedMessage shouldEqual message
    }
}