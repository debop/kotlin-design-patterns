package io.kommons.utils

import io.kommons.logging.KLogging
import java.util.Properties

/**
 *  Provide System Property values
 *
 * @author debop
 */
object Systemx: KLogging() {

    const val USER_DIR = "user.dir"
    const val USER_NAME = "user.name"
    const val USER_HOME = "user.home"
    const val JAVA_HOME = "java.home"
    const val TEMP_DIR = "java.io.tmpdir"
    const val OS_NAME = "os.name"
    const val OS_VERSION = "os.version"
    const val JAVA_VERSION = "java.version"
    const val JAVA_CLASS_VERION = "java.class.version"
    const val JAVA_SPECIFICATION_VERSION = "java.specification.version"
    const val JAVA_VENDOR = "java.vendor"
    const val JAVA_CLASSPATH = "java.class.path"
    const val PATH_SEPARATOR = "path.separator"
    const val HTTP_PROXY_HOST = "http.proxyHost"
    const val HTTP_PROXY_PORT = "http.proxyPort"
    const val HTTP_PROXY_USER = "http.proxyUser"
    const val HTTP_PROXY_PASSWORD = "http.proxyPassword"
    const val FILE_ENCODING = "file.encoding"
    const val SUN_BOOT_CLASS_PATH = "sun.boot.class.path"

    /** Runtime package */
    @JvmStatic
    val RuntimePackage: Package by lazy { Runtime::class.java.`package` }

    /** System Properties */
    @JvmStatic
    val SystemProps: Properties by lazy { System.getProperties() }

    /** CPU Core count */
    @JvmStatic
    val ProcessCount: Int by lazy { Runtime.getRuntime().availableProcessors() }

    @JvmStatic
    val JavaCompiler: String by lazy { System.getProperty("java.compiler") }

    /** JVM 버전 */
    @JvmStatic
    val JavaVersion: String by lazy { RuntimePackage.specificationVersion }

    /** JVM 구현 버전 */
    @JvmStatic
    val JavaImplementationVersion: String by lazy { RuntimePackage.implementationVersion }

    /** JVM 벤더 */
    @JvmStatic
    val JavaVendor: String by lazy { RuntimePackage.specificationVendor }

    @JvmStatic
    val JavaVendorUrl: String by lazy { System.getProperty("java.vendor.url") }

    /** JVM 구현 벤더  */
    @JvmStatic
    val JavaImplementationVendor: String by lazy { RuntimePackage.implementationVendor }

    @JvmStatic
    val JavaClassVersion: String by lazy { System.getProperty(JAVA_CLASS_VERION) }

    @JvmStatic
    val JavaLibraryPath: String by lazy { System.getProperty("java.library.path") }

    @JvmStatic
    val JavaRuntimeName: String by lazy { System.getProperty("java.runtime.name") }

    @JvmStatic
    val JavaRuntimeVersion: String by lazy { System.getProperty("java.runtime.version") }

    @JvmStatic
    val JavaSpecificationName: String by lazy { System.getProperty("java.specification.name") }

    @JvmStatic
    val JavaSpecificationVendor: String by lazy { System.getProperty("java.specification.vendor") }

    @JvmStatic
    val IsJava6: Boolean by lazy { JavaVersion == "1.6" }

    @JvmStatic
    val IsJava7: Boolean by lazy { JavaVersion == "1.7" }

    @JvmStatic
    val IsJava8: Boolean by lazy { JavaVersion == "1.8" }

    @JvmStatic
    val IsJava9: Boolean by lazy { JavaVersion == "1.9" }

    /** JVM home directory */
    @JvmStatic
    val JavaHome: String by lazy { System.getProperty("java.home") }

    @JvmStatic
    val LineSeparator: String by lazy { System.getProperty("line.separator") }

    @JvmStatic
    val FileSeparator: String by lazy { System.getProperty("file.separator") }

    @JvmStatic
    val PathSeparator: String by lazy { System.getProperty("path.separator") }

    @JvmStatic
    val FileEncoding: String by lazy { System.getProperty("file.encoding") }

    @JvmStatic
    val UserName: String by lazy { System.getProperty(USER_NAME) }

    @JvmStatic
    val UserHome: String by lazy { System.getProperty(USER_HOME) }

    @JvmStatic
    val UserDir: String by lazy { System.getProperty(USER_DIR) }

    @JvmStatic
    val UserCountry: String by lazy { System.getProperty("user.country") ?: System.getProperty("user.region") }

    @JvmStatic
    val TempDir: String by lazy { System.getProperty(TEMP_DIR) }

    @JvmStatic
    val JavaIOTmpDir: String by lazy { System.getProperty(TEMP_DIR) }

    @JvmStatic
    val OSName: String by lazy { System.getProperty(OS_NAME) }

    @JvmStatic
    val OSVersion: String by lazy { System.getProperty(OS_VERSION) }

    @JvmStatic
    val isWindows: Boolean by lazy { OSName.contains("win") }

    @JvmStatic
    val isMac: Boolean by lazy { OSName.contains("mac") }

    @JvmStatic
    val isSolaris: Boolean by lazy { OSName.contains("sunos") }

    @JvmStatic
    val isUnix: Boolean by lazy { OSName.contains("nix") || OSName.contains("nux") || OSName.contains("aix") }

}