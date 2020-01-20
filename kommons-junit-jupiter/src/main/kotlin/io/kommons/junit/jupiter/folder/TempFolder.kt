package io.kommons.junit.jupiter.folder

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import io.kommons.logging.info
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource
import java.io.Closeable
import java.io.File
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitResult.CONTINUE
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

/**
 * 테스트용 임시 폴더 관리를 수행합니다.
 *
 * @author debop
 */
class TempFolder: CloseableResource, Closeable {

    companion object: KLogging() {
        private const val PREFIX = "kommons_"
    }

    private val rootPath: Path
    private val rootFolder: File

    init {
        try {
            rootPath = Files.createTempDirectory(PREFIX)
            rootFolder = rootPath.toFile()
            log.info { "Create temporary folder. [$rootPath]" }
        } catch (e: IOException) {
            throw TempFolderException("Fail to create temporary root folder for testing", e)
        }
    }

    val root: File get() = rootFolder

    fun createFile(): File {
        return try {
            val file = Files.createTempFile(rootPath, PREFIX, null).toFile()
            log.debug { "Create temporary file. file=[$file]" }
            file
        } catch (e: IOException) {
            throw TempFolderException("Fail to create temporary file.")
        }
    }

    fun createFile(filename: String): File {
        return try {
            val path = Paths.get(root.path, filename)
            log.debug { "Create temporary file. [$path]" }
            Files.createFile(path).toFile()
        } catch (e: IOException) {
            throw TempFolderException("Fail to create temporary file. filename=$filename")
        }
    }

    fun createDirectory(dir: String): File {
        return try {
            val path = Paths.get(root.path, dir)
            log.debug { "Create temporary directory. [$path]" }
            Files.createDirectory(path).toFile()
        } catch (e: IOException) {
            throw TempFolderException("Fail to create temporary directory. dir=$dir")
        }
    }


    override fun close() {
        runCatching { destroy() }
    }

    private fun destroy() {
        log.info { "Delete temporary folder. [$rootPath]" }
        if (root.exists()) {
            try {
                Files.walkFileTree(rootPath, CleanupFileVisitor())

                if (root.exists()) {
                    Files.delete(rootPath)
                }
            } catch (e: IOException) {
                throw TempFolderException("Fail to delete temporary folder. root=[$rootPath]")
            }
        }
    }

    private class CleanupFileVisitor: SimpleFileVisitor<Path>() {
        override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            return file?.run { delete(this) } ?: CONTINUE
        }

        override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
            return dir?.run { delete(this) } ?: CONTINUE
        }

        private fun delete(path: Path): FileVisitResult {
            runCatching { Files.delete(path) }
            return CONTINUE
        }
    }


    class TempFolderException: RuntimeException {
        constructor(): super()
        constructor(msg: String): super(msg)
        constructor(msg: String, cause: Throwable?): super(msg, cause)
        constructor(cause: Throwable?): super(cause)
    }

}