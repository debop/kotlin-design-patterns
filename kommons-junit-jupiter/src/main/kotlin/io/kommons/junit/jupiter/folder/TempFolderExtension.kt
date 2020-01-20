package io.kommons.junit.jupiter.folder

import io.kommons.logging.KLogging
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Namespace
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

/**
 * TemporaryFolderExtension
 *
 * 임시 폴더를 테스트 클래스 단위로 생성 ( `@BeforeAll` 에서 temporary folder 생성하기 )
 * ```
 * @ExtendWith(TemporaryFolderExtension::class)
 * class TemporaryFolderExtensionBeforeAllTest {
 *
 * lateinit var tempFolder: TemporaryFolder
 *
 * @BeforeAll
 * fun beforeAll(tempFolder: TemporaryFolder) {
 *     this.tempFolder = tempFolder
 * }
 * ```
 *
 * `@BeforeEach` 에서 temporary folder 생성하기
 *
 * @ExtendWith(TemporaryFolderExtension::class)
 * class TemporaryFolderExtensionBeforeEachTest {
 *
 * lateinit var tempFolder: TemporaryFolder
 *
 * @BeforeEach
 * fun setup(tempFolder: TemporaryFolder) {
 *     this.tempFolder = tempFolder
 * }
 *
 *
 * 함수별로 임시폴더를 생성
 * ```
 * @Test
 * @ExtendWith(TemporaryFolderExtension::class)
 * fun `인자로 temporary folder를 받을 수 있다`(tempFolder: TemporaryFolder) {
 *     tempFolder.createFile("foo.txt").exists().shouldBeTrue()
 *     tempFolder.createDirectory("bar").exists().shouldBeTrue()
 * }
 * ```
 *
 * @author debop
 */
class TempFolderExtension: ParameterResolver {

    companion object: KLogging() {
        private val NAMESPACE = Namespace.create(TempFolderExtension::class.java)
    }

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return parameterContext.parameter.type == TempFolder::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return extensionContext
            .getStore(NAMESPACE)
            .getOrComputeIfAbsent(parameterContext,
                                  { TempFolder() },
                                  TempFolder::class.java)
    }
}