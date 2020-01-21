package io.kommons.designpatterns.execute.around

import io.kommons.junit.jupiter.folder.TempFolder
import io.kommons.junit.jupiter.folder.TempFolderExtension
import io.kommons.logging.KLogging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * WithFileWriterTest
 *
 * @author debop
 */
@ExtendWith(TempFolderExtension::class)
class WithFileWriterTest {

    companion object: KLogging()

    @Test
    fun `with file writer`(tempFolder: TempFolder) {
        val tempFile = tempFolder.createFile("withfile.txt")

        withFileWriter(tempFile.path) {
            write("Hello")
            append(" ")
            append("there!")
        }
    }
}