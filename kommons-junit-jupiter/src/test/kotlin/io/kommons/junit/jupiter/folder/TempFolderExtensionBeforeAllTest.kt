package io.kommons.junit.jupiter.folder

import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContainAll
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import java.nio.file.Files
import kotlin.streams.toList

@TempFolderTest
@TestInstance(Lifecycle.PER_CLASS)
class TempFolderExtensionBeforeAllTest {

    lateinit var tempFolder: TempFolder

    @BeforeAll
    fun beforeAll(tempFolder: TempFolder) {
        this.tempFolder = tempFolder
    }

    @AfterAll
    fun afterAll() {
        val createdFiles = Files.list(tempFolder.root.toPath()).map { it.toFile().name }.toList()
        createdFiles.size shouldEqualTo 2
        createdFiles shouldContainAll listOf("foo.txt", "bar")
    }


    @Test
    fun `임시 파일 생성`() {
        val file = tempFolder.createFile("foo.txt")
        file.exists().shouldBeTrue()
    }

    @Test
    fun `임시 디렉토리 생성`() {
        val dir = tempFolder.createDirectory("bar")
        dir.exists().shouldBeTrue()
    }
}