import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {

    base
    `maven-publish`
    jacoco
    kotlin("jvm") version Versions.kotlin apply false

    // see: https://kotlinlang.org/docs/reference/compiler-plugins.html
    kotlin("plugin.spring") version Versions.kotlin apply false
    kotlin("plugin.allopen") version Versions.kotlin apply false
    kotlin("plugin.noarg") version Versions.kotlin apply false
    kotlin("plugin.jpa") version Versions.kotlin apply false

    id(BuildPlugins.dokka) version BuildPlugins.Versions.dokka apply false
    id(BuildPlugins.dependency_management) version BuildPlugins.Versions.dependency_management
    id(BuildPlugins.spring_boot) version BuildPlugins.Versions.spring_boot apply false
}

val projectGroup: String by project
val baseVersion: String by project
val snapshotVersion: String by project

allprojects {

    group = projectGroup
    version = baseVersion + snapshotVersion

    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {

    apply {
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()

        plugin("jacoco")
        plugin("maven-publish")
        plugin("org.jetbrains.dokka")
        plugin("io.spring.dependency-management")
    }

    tasks.withType<KotlinCompile> {
        sourceCompatibility = JavaVersion.VERSION_11.toString()
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
            freeCompilerArgs = listOf("-Xjsr305=strict",
                                      "-Xjvm-default=enable",
                                      "-Xinline-classes",
                                      "-progressive")

            val experimentalAnnotations = listOf("kotlin.Experimental",
                                                 "`kotlin.ExperimentalStdlibApi`",
                                                 "kotlin.experimental.ExperimentalTypeInference",
                                                 "kotlin.ExperimentalMultiplatform",
                                                 "kotlinx.coroutines.ExperimentalCoroutinesApi",
                                                 "kotlinx.coroutines.ObsoleteCoroutinesApi",
                                                 "kotlinx.coroutines.InternalCoroutinesApi",
                                                 "kotlinx.coroutines.FlowPreview")
            freeCompilerArgs = freeCompilerArgs.plus(experimentalAnnotations.map { "-Xuse-experimental=$it" })
        }
    }

    val sourceSets = project.the<SourceSetContainer>()

    val sourcesJar by tasks.creating(Jar::class) {
        from(sourceSets["main"].allSource)
        archiveClassifier.set("sources")
    }

    // Configure existing Dokka task to output HTML to typical Javadoc directory
    val dokka by tasks.getting(DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"

        externalDocumentationLink { url = URL("https://docs.oracle.com/javase/8/docs/api/") }
        // commons-io 가 제대로 Link 되는데도, dokka에서 예외를 발생시킨다. 우선은 link 안되게 막음 
        // externalDocumentationLink { url = URL("https://commons.apache.org/proper/commons-io/javadocs/api-2.6/") }
    }

    // Create dokka Jar task from dokka task output
    val dokkaJar by tasks.creating(Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles Kotlin docs with Dokka"
        archiveClassifier.set("javadoc")
        // dependsOn(tasks.dokka) not needed; dependency automatically inferred by from(tasks.dokka)
        from(dokka)
    }

    tasks.withType<Test> {
        useJUnitPlatform()

        testLogging {
            events("failed")
        }
    }

    // jacoco
    configure<JacocoPluginExtension> {
    }

    tasks.withType<JacocoReport> {
        reports {
            html.isEnabled = true
            xml.isEnabled = true
            csv.isEnabled = false
        }
    }

    tasks["clean"].doLast {
        delete("./.project")
        delete("./out")
        delete("./bin")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:${Versions.spring_boot}")
        }
        dependencies {
            dependency(Libraries.kotlin_stdlib)
            dependency(Libraries.kotlin_stdlib_common)
            dependency(Libraries.kotlin_stdlib_jdk7)
            dependency(Libraries.kotlin_stdlib_jdk8)
            dependency(Libraries.kotlin_reflect)
            dependency(Libraries.kotlin_test)
            dependency(Libraries.kotlin_test_common)
            dependency(Libraries.kotlin_test_junit5)

            dependency(Libraries.kotlinx_coroutines_core)
            dependency(Libraries.kotlinx_coroutines_jdk7)
            dependency(Libraries.kotlinx_coroutines_jdk8)
            dependency(Libraries.kotlinx_coroutines_reactor)
            dependency(Libraries.kotlinx_coroutines_rx2)

            dependency(Libraries.kotlinx_io)
            dependency(Libraries.kotlinx_io_jvm)
            dependency(Libraries.kotlinx_coroutines_io_jvm)

            // Apache Commons
            dependency(Libraries.commons_collections4)
            dependency(Libraries.commons_lang3)
            dependency(Libraries.commons_compress)
            dependency(Libraries.commons_codec)
            dependency(Libraries.commons_csv)
            dependency(Libraries.commons_math3)
            dependency(Libraries.commons_pool2)
            dependency(Libraries.commons_text)
            dependency(Libraries.commons_exec)
            dependency(Libraries.commons_io)

            dependency(Libraries.slf4j_api)
            dependency(Libraries.logback)

            dependency(Libraries.zerolog_core)
            dependency(Libraries.zerolog_slf4j)

            dependency(Libraries.findbugs)
            dependency(Libraries.guava)
            dependency(Libraries.joda_time)

            dependency(Libraries.fst)
            dependency(Libraries.kryo)
            dependency(Libraries.kryo_serializers)

            // Resilience4j
            dependency(Libraries.resilience4j_all)
            dependency(Libraries.resilience4j_annotations)
            dependency(Libraries.resilience4j_bulkhead)
            dependency(Libraries.resilience4j_cache)
            dependency(Libraries.resilience4j_circuitbreaker)
            dependency(Libraries.resilience4j_core)
            dependency(Libraries.resilience4j_feign)
            dependency(Libraries.resilience4j_framework_common)
            dependency(Libraries.resilience4j_kotlin)
            dependency(Libraries.resilience4j_metrics)
            dependency(Libraries.resilience4j_micrometer)
            dependency(Libraries.resilience4j_prometheus)
            dependency(Libraries.resilience4j_ratelimiter)
            dependency(Libraries.resilience4j_reactor)
            dependency(Libraries.resilience4j_retrofit)
            dependency(Libraries.resilience4j_retry)
            dependency(Libraries.resilience4j_rxjava2)
            dependency(Libraries.resilience4j_spring)
            dependency(Libraries.resilience4j_spring_boot2)
            dependency(Libraries.resilience4j_spring_boot_common)

            // Vavr
            dependency(Libraries.vavr)
            dependency(Libraries.vavr_jackson)
            dependency(Libraries.vavr_kotlin)
            dependency(Libraries.vavr_match)
            dependency(Libraries.vavr_test)

            // Netty
            dependency(Libraries.netty_all)
            dependency(Libraries.netty_common)
            dependency(Libraries.netty_buffer)
            dependency(Libraries.netty_codec)
            dependency(Libraries.netty_codec_dns)
            dependency(Libraries.netty_codec_http)
            dependency(Libraries.netty_codec_http2)
            dependency(Libraries.netty_codec_socks)
            dependency(Libraries.netty_handler)
            dependency(Libraries.netty_handler_proxy)
            dependency(Libraries.netty_resolver)
            dependency(Libraries.netty_resolver_dns)
            dependency(Libraries.netty_transport)
            dependency(Libraries.netty_transport_native_epoll)
            dependency(Libraries.netty_transport_native_kqueue)

            // Jackson
            dependency(Libraries.jackson_annotations)
            dependency(Libraries.jackson_core)
            dependency(Libraries.jackson_databind)
            dependency(Libraries.jackson_datatype_jsr310)
            dependency(Libraries.jackson_datatype_jdk8)
            dependency(Libraries.jackson_datatype_joda)
            dependency(Libraries.jackson_datatype_guava)

            dependency(Libraries.jackson_module_paranamer)
            dependency(Libraries.jackson_module_parameter_names)
            dependency(Libraries.jackson_module_kotlin)
            dependency(Libraries.jackson_module_afterburner)

            // Micrometer
            dependency(Libraries.micrometer_core)
            dependency(Libraries.micrometer_test)
            dependency(Libraries.micrometer_registry)
            dependency(Libraries.micrometer_registry_prometheus)
            dependency(Libraries.micrometer_registry_graphite)
            dependency(Libraries.micrometer_registry_jmx)

            // Reactor
            dependency(Libraries.reactor_core)
            dependency(Libraries.reactor_test)
            dependency(Libraries.reactor_netty)

            dependency(Libraries.rxjava2)
            dependency(Libraries.rxkotlin)

            dependency(Libraries.mongo_java_driver)
            dependency(Libraries.mongo_bson)
            dependency(Libraries.mongo_driver)
            dependency(Libraries.mongo_driver_async)
            dependency(Libraries.mongo_driver_core)
            dependency(Libraries.mongo_driver_reactivestreams)

            // Hibernate
            dependency(Libraries.hibernate_core)
            dependency(Libraries.hibernate_jcache)
            dependency(Libraries.hibernate_jpa_2_1_api)
            dependency(Libraries.javassist)
            dependency(Libraries.querydsl_apt)
            dependency(Libraries.querydsl_jpa)

            // Validators
            dependency(Libraries.validation_api)
            dependency(Libraries.hibernate_validator)
            dependency(Libraries.hibernate_validator_annotation_processor)
            dependency(Libraries.javax_el_api)
            dependency(Libraries.javax_el)

            dependency(Libraries.hikaricp)
            dependency(Libraries.mysql_connector_java)
            dependency(Libraries.mariadb_java_client)
            dependency(Libraries.h2)

            // Cache
            dependency(Libraries.cache_api)
            dependency(Libraries.cache2k_all)
            dependency(Libraries.cache2k_spring)
            dependency(Libraries.cache2k_jcache)

            // Dagger
            dependency(Libraries.dagger)
            dependency(Libraries.dagger_compiler)

            // Koin
            dependency(Libraries.koin_core)
            dependency(Libraries.koin_core_ext)
            dependency(Libraries.koin_test)

            // Metrics
            dependency(Libraries.latencyUtils)
            dependency(Libraries.hdrHistogram)

            dependency(Libraries.byte_buddy)
            dependency(Libraries.byte_buddy_agent)

            dependency(Libraries.objenesis)
            dependency(Libraries.ow2_asm)

            dependency(Libraries.random_beans)
            dependency(Libraries.reflectasm)

            dependency(Libraries.junit_jupiter)
            dependency(Libraries.junit_jupiter_api)
            dependency(Libraries.junit_jupiter_engine)
            dependency(Libraries.junit_jupiter_params)
            dependency(Libraries.junit_jupiter_migrationsupport)

            dependency(Libraries.junit_platform_commons)
            dependency(Libraries.junit_platform_engine)

            dependency(Libraries.kluent)
            dependency(Libraries.assertj_core)

            dependency(Libraries.mockk)
            dependency(Libraries.mockito_core)
            dependency(Libraries.mockito_junit_jupiter)
            dependency(Libraries.mockito_kotlin)

            dependency(Libraries.testcontainers)
        }
    }

    dependencies {
        val api by configurations
        val testApi by configurations
        val compile by configurations
        val compileOnly by configurations
        val testCompile by configurations
        val implementation by configurations
        val testImplementation by configurations

        val testRuntimeOnly by configurations

        implementation(Libraries.kotlin_stdlib_jdk8)
        implementation(Libraries.kotlin_reflect)
        testImplementation(Libraries.kotlin_test)
        testImplementation(Libraries.kotlin_test_junit5)

        if (!project.name.contains("kommons")) {
            api(project(":kommons-logging"))
            testApi(project(":kommons-junit-jupiter"))
        }

        api(Libraries.slf4j_api)
        api(Libraries.logback)
        testApi(Libraries.zerolog_core)
        testApi(Libraries.zerolog_slf4j)

        testApi(Libraries.junit_jupiter)
        testApi(Libraries.junit_jupiter_migrationsupport)
        testRuntimeOnly(Libraries.junit_platform_engine)

        testApi(Libraries.kluent)
        testImplementation(Libraries.assertj_core)
        testImplementation(Libraries.mockk)

        testImplementation(Libraries.testcontainers)
    }

    tasks.withType<Jar> {
        manifest.attributes["Specification-Title"] = project.name
        manifest.attributes["Specification-Version"] = project.version
        manifest.attributes["Implementation-Title"] = project.name
        manifest.attributes["Implementation-Version"] = project.version
        manifest.attributes["Automatic-Module-Name"] = project.name.replace('-', '.')
        manifest.attributes["Created-By"] =
            "${System.getProperty("java.version")} (${System.getProperty("java.specification.vendor")})"
    }

    /*
        1. mavenLocal 에 publish 시에는 ./gradlew publishMavenPublicationToMavenLocalRepository 를 수행
        2. nexus에 publish 시에는 ./gradlew publish -PnexusDeployPassword=디플로이 로 비밀번호를 넣어줘야 배포가 됩니다.

        Release 를 위해서는 아래와 같이 `nexusDeployPassword`에 비밀번호를 넣고, `snapshotVersion`에 아무 것도 지정하지 않으면
        nexus server의 releases 에 등록됩니다.
        
        ```bash
        $ ./gradlew clean build
        $ ./gradlew publish -PnexusDeployPassword=elvmffhdl -PsnapshotVersion=
        ```
     */
    publishing {
        publications {
            if (!project.path.contains("sample")) {
                register("Maven", MavenPublication::class) {
                    from(components["java"])
                    artifact(sourcesJar)
                    artifact(dokkaJar)
                }
            }
        }
        repositories {
            mavenLocal()
        }
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}
