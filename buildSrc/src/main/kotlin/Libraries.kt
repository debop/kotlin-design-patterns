object BuildPlugins {

    object Versions {
        const val detekt = "1.2.2"
        const val dokka = "0.9.18"
        const val dependency_management = "1.0.8.RELEASE"
        const val jooq = "3.0.3"
        const val protobuf = "0.8.10"
        const val avro = "0.17.0"
        const val jarTest = "1.0.1"
        const val spring_boot = "2.2.2.RELEASE"
    }

    const val detekt = "io.gitlab.arturbosch.detekt"
    const val dokka = "org.jetbrains.dokka"
    const val dependency_management = "io.spring.dependency-management"
    const val spring_boot = "org.springframework.boot"

    const val jooq = "nu.studer.jooq"
    // https://github.com/google/protobuf-gradle-plugin
    const val protobuf = "com.google.protobuf"

    // https://github.com/davidmc24/gradle-avro-plugin
    const val avro = "com.commercehub.gradle.plugin.avro"

    const val jarTest = "com.github.hauner.jarTest"
}

object Versions {

    const val kotlin = "1.3.61"
    const val kotlinx_coroutines = "1.3.3"
    const val kotlinx_io = "0.1.16"
    const val ktor = "1.2.6"
    const val arrow = "0.10.4"

    const val spring_boot = BuildPlugins.Versions.spring_boot
    const val resilience4j = "1.2.0"
    const val hystrix = "1.5.12"
    const val vavr = "0.10.0"
    const val netty = "4.1.43.Final"

    const val aws = "1.11.667"

    const val grpc = "1.23.0"
    const val protobuf = "3.8.0"
    const val krotoplus = "0.4.0"
    const val avro = "1.9.1"   // 1.9.+ 은 jackson-dataformat-avro 에서 아직 지원하지 않습니다.

    const val retrofit2 = "2.7.0"

    const val jasync_sql = "1.0.12"

    const val reactor = "3.3.0.RELEASE"
    const val jackson = "2.10.1"

    const val random_beans = "3.9.0"
    const val reflectasm = "1.11.9"
    const val mongo_driver = "3.12.0"
    const val lettuce = "5.2.1.RELEASE"
    const val redisson = "3.11.6"

    const val hibernate = "5.4.10.Final"
    const val hibernate_validator = "6.1.0.Final"
    const val querydsl = "4.2.1"
    const val jooq = "3.12.3"

    const val slf4j = "1.7.29"
    const val logback = "1.2.3"
    const val zerolog = "0.30.0"

    const val micrometer = "1.3.2"
    const val metrics = "4.1.2"
    const val cache2k = "1.2.4.Final"

    const val ignite = "2.7.6"
    const val hazelcast = "3.12.4"
    const val cassandra = "4.3.0"
    const val elasticsearch = "7.5.1"

    const val eclipse_collections = "10.1.0"

    const val koin = "2.0.1"

    const val byte_buddy = "1.10.6"

    const val junit_jupiter = "5.5.2"
    const val junit_platform = "1.5.2"
    const val assertj_core = "3.12.2"
    const val mockk = "1.9.3"
    const val mockito = "3.2.4"
    const val testcontainers = "1.12.4"
}

object Libraries {

    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlin_stdlib_common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
    const val kotlin_stdlib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val kotlin_test = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    const val kotlin_test_common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
    const val kotlin_test_junit5 = "org.jetbrains.kotlin:kotlin-test-junit5:${Versions.kotlin}"

    const val kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_jdk7 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk7:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_jdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_reactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_debug = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinx_coroutines}"

    const val kotlinx_io = "org.jetbrains.kotlinx:kotlinx-io:${Versions.kotlinx_io}"
    const val kotlinx_io_jvm = "org.jetbrains.kotlinx:kotlinx-io-jvm:${Versions.kotlinx_io}"
    const val kotlinx_coroutines_io_jvm = "org.jetbrains.kotlinx:kotlinx-coroutines-io-jvm:${Versions.kotlinx_io}"

    // Ktor
    const val ktor_client_apache = "io.ktor:ktor-client-apache:${Versions.ktor}"
    const val ktor_client_cio = "io.ktor:ktor-client-cio:${Versions.ktor}"

    // Arrow
    const val arrow_core = "io.arrow-kt:arrow-core:${Versions.arrow}"
    const val arrow_syntax = "io.arrow-kt:arrow-syntax:${Versions.arrow}"
    const val arrow_meta = "io.arrow-kt:arrow-meta:${Versions.arrow}"

    // javax api
    const val activation_api = "javax.activation:javax.activation-api:1.2.0"
    const val annotation_api = "javax.annotation:javax.annotation-api:1.3.2"
    const val cache_api = "javax.cache:cache-api:1.1.1"
    const val javax_inject = "javax.inject:javax.inject:1"
    const val validation_api = "javax.validation:validation-api:2.0.1.Final"
    const val javax_servlet_api = "javax.servlet:javax.servlet-api:4.0.1"

    // Apache Commons
    const val commons_beanutils = "commons-beanutils:commons-beanutils:1.9.4"
    const val commons_compress = "org.apache.commons:commons-compress:1.18"
    const val commons_codec = "commons-codec:commons-codec:1.12"
    const val commons_collections4 = "org.apache.commons:commons-collections4:4.3"
    const val commons_csv = "org.apache.commons:commons-csv:1.7"
    const val commons_digest3 = "org.apache.commons:commons-digester3:3.2"
    const val commons_exec = "org.apache.commons:commons-exec:1.3"
    const val commons_io = "commons-io:commons-io:2.6"
    const val commons_lang3 = "org.apache.commons:commons-lang3:3.9"
    const val commons_logging = "commons-logging:commons-logging:1.2"
    const val commons_math3 = "org.apache.commons:commons-math3:3.6.1"
    const val commons_pool2 = "org.apache.commons:commons-pool2:2.6.2"
    const val commons_rng_simple = "org.apache.commons:commons-rng-simple:1.2"
    const val commons_text = "org.apache.commons:commons-text:1.6"
    const val commons_validator = "commons-validator:commons-validator:1.6"

    const val colt = "colt:colt:1.2.0"

    // typesafe config
    const val typesafe_config = "com.typesafe:config:1.3.4"

    const val slf4j_api = "org.slf4j:slf4j-api:${Versions.slf4j}"
    const val slf4j_simple = "org.slf4j:slf4j-simple:${Versions.slf4j}"
    const val slf4j_log4j12 = "org.slf4j:slf4j-log4j12:${Versions.slf4j}"
    const val jcl_over_slf4j = "org.slf4j:jcl-over-slf4j:${Versions.slf4j}"
    const val jul_to_slf4j = "org.slf4j:jul-to-slf4j:${Versions.slf4j}"
    const val log4j_over_slf4j = "org.slf4j:log4j-over-slf4j:${Versions.slf4j}"

    const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"

    const val zerolog_core = "com.obsidiandynamics.zerolog:zerolog-core:${Versions.zerolog}"
    const val zerolog_slf4j = "com.obsidiandynamics.zerolog:zerolog-slf4j17:${Versions.zerolog}"

    const val findbugs = "com.google.code.findbugs:jsr305:3.0.2"
    const val guava = "com.google.guava:guava:28.0-jre"
    const val joda_time = "joda-time:joda-time:2.10.2"
    const val joda_convert = "org.joda:joda-convert:2.2.1"

    const val eclipse_collections = "org.eclipse.collections:eclipse-collections:${Versions.eclipse_collections}"
    const val eclipse_collections_forkjoin = "org.eclipse.collections:eclipse-collections-forkjoin:${Versions.eclipse_collections}"
    const val eclipse_collections_testutils = "org.eclipse.collections:eclipse-collections-testutils:${Versions.eclipse_collections}"

    const val fst = "de.ruedigermoeller:fst:2.57"
    const val kryo = "com.esotericsoftware:kryo:4.0.2"
    const val kryo_serializers = "de.javakaffee:kryo-serializers:0.45"

    // Resilience4j
    const val resilience4j_all = "io.github.resilience4j:resilience4j-all:${Versions.resilience4j}"
    const val resilience4j_annotations = "io.github.resilience4j:resilience4j-annotations:${Versions.resilience4j}"
    const val resilience4j_bulkhead = "io.github.resilience4j:resilience4j-bulkhead:${Versions.resilience4j}"
    const val resilience4j_cache = "io.github.resilience4j:resilience4j-cache:${Versions.resilience4j}"
    const val resilience4j_circuitbreaker = "io.github.resilience4j:resilience4j-circuitbreaker:${Versions.resilience4j}"
    const val resilience4j_core = "io.github.resilience4j:resilience4j-core:${Versions.resilience4j}"
    const val resilience4j_feign = "io.github.resilience4j:resilience4j-feign:${Versions.resilience4j}"
    const val resilience4j_framework_common = "io.github.resilience4j:resilience4j-framework-common:${Versions.resilience4j}"
    const val resilience4j_kotlin = "io.github.resilience4j:resilience4j-kotlin:${Versions.resilience4j}"
    const val resilience4j_metrics = "io.github.resilience4j:resilience4j-metrics:${Versions.resilience4j}"
    const val resilience4j_micrometer = "io.github.resilience4j:resilience4j-micrometer:${Versions.resilience4j}"
    const val resilience4j_prometheus = "io.github.resilience4j:resilience4j-prometheus:${Versions.resilience4j}"
    const val resilience4j_ratelimiter = "io.github.resilience4j:resilience4j-ratelimiter:${Versions.resilience4j}"
    const val resilience4j_reactor = "io.github.resilience4j:resilience4j-reactor:${Versions.resilience4j}"
    const val resilience4j_retrofit = "io.github.resilience4j:resilience4j-retrofit:${Versions.resilience4j}"
    const val resilience4j_rxjava2 = "io.github.resilience4j:resilience4j-rxjava2:${Versions.resilience4j}"
    const val resilience4j_retry = "io.github.resilience4j:resilience4j-retry:${Versions.resilience4j}"
    const val resilience4j_spring = "io.github.resilience4j:resilience4j-spring:${Versions.resilience4j}"
    const val resilience4j_spring_boot2 = "io.github.resilience4j:resilience4j-spring-boot2:${Versions.resilience4j}"
    const val resilience4j_spring_boot_common = "io.github.resilience4j:resilience4j-spring-boot-common:${Versions.resilience4j}"

    // Failsafe (recommand use resilience4j)
    const val jodah_failsafe = "net.jodah:failsafe:2.1.0"
    const val jodah_concurrentunit = "net.jodah:concurrentunit:0.4.4"

    // Hystrix
    const val hystrix_core = "com.netflix.hystrix:hystrix-core:${Versions.hystrix}"
    const val hystrix_metrics_event_stream = "com.netflix.hystrix:hystrix-metrics-event-stream:${Versions.hystrix}"
    const val hystrix_javanica = "com.netflix.hystrix:hystrix-javanica:${Versions.hystrix}"
    const val hystrix_junit = "com.netflix.hystrix:hystrix-junit:${Versions.hystrix}"

    // Vavr
    const val vavr = "io.vavr:vavr:${Versions.vavr}"
    const val vavr_jackson = "io.vavr:vavr-jackson:${Versions.vavr}"
    const val vavr_kotlin = "io.vavr:vavr-kotlin:${Versions.vavr}"
    const val vavr_match = "io.vavr:vavr-match:${Versions.vavr}"
    const val vavr_test = "io.vavr:vavr-test:${Versions.vavr}"

    // Netty
    const val netty_all = "io.netty:netty-all:${Versions.netty}"
    const val netty_common = "io.netty:netty-common:${Versions.netty}"
    const val netty_buffer = "io.netty:netty-buffer:${Versions.netty}"
    const val netty_codec = "io.netty:netty-codec:${Versions.netty}"
    const val netty_codec_dns = "io.netty:netty-codec-dns:${Versions.netty}"
    const val netty_codec_http = "io.netty:netty-codec-http:${Versions.netty}"
    const val netty_codec_http2 = "io.netty:netty-codec-http2:${Versions.netty}"
    const val netty_codec_socks = "io.netty:netty-codec-socks:${Versions.netty}"
    const val netty_handler = "io.netty:netty-handler:${Versions.netty}"
    const val netty_handler_proxy = "io.netty:netty-handler-proxy:${Versions.netty}"
    const val netty_resolver = "io.netty:netty-resolver:${Versions.netty}"
    const val netty_resolver_dns = "io.netty:netty-resolver-dns:${Versions.netty}"
    const val netty_transport = "io.netty:netty-transport:${Versions.netty}"
    const val netty_transport_native_epoll = "io.netty:netty-transport-native-epoll:${Versions.netty}"
    const val netty_transport_native_kqueue = "io.netty:netty-transport-native-kqueue:${Versions.netty}"

    // gRPC
    const val grpc_protobuf = "io.grpc:grpc-protobuf:${Versions.grpc}"
    const val grpc_stub = "io.grpc:grpc-stub:${Versions.grpc}"
    const val grpc_netty = "io.grpc:grpc-netty:${Versions.grpc}"
    const val grpc_netty_shaded = "io.grpc:grpc-netty-shaded:${Versions.grpc}"
    const val grpc_protoc_gen_grpc_java = "io.grpc:protoc-gen-grpc-java:${Versions.grpc}"
    const val grpc_testing = "io.grpc:grpc-testing:${Versions.grpc}"

    const val protobuf_protoc = "com.google.protobuf:protoc:${Versions.protobuf}"
    const val protobuf_java = "com.google.protobuf:protobuf-java:${Versions.protobuf}"
    const val protobuf_java_util = "com.google.protobuf:protobuf-java-util:${Versions.protobuf}"

    const val avro = "org.apache.avro:avro:${Versions.avro}"
    const val avro_ipc = "org.apache.avro:avro-ipc:${Versions.avro}"
    const val avro_ipc_netty = "org.apache.avro:avro-ipc-netty:${Versions.avro}"
    const val avro_compiler = "org.apache.avro:avro-compiler:${Versions.avro}"
    const val avro_protobuf = "org.apache.avro:avro-protobuf:${Versions.avro}"

    const val grpc_kotlin_gen = "io.rouz:grpc-kotlin-gen:0.1.1"

    // kroto-plus
    const val kroto_plus_coroutines = "com.github.marcoferrer.krotoplus:kroto-plus-coroutines:${Versions.krotoplus}"
    const val kroto_plus_message = "com.github.marcoferrer.krotoplus:kroto-plus-message:${Versions.krotoplus}"
    const val kroto_plus_protoc_gen_kroto_plus = "com.github.marcoferrer.krotoplus:protoc-gen-kroto-plus:${Versions.krotoplus}"
    const val kroto_plus_test = "com.github.marcoferrer.krotoplus:kroto-plus-test:${Versions.krotoplus}"
    const val kroto_plus_protoc_gen_grpc_coroutines = "com.github.marcoferrer.krotoplus:protoc-gen-grpc-coroutines:${Versions.krotoplus}"

    // Amazon
    const val aws_java_sdk = "com.amazonaws:aws-java-sdk:${Versions.aws}"
    const val aws_java_sdk_s3 = "com.amazonaws:aws-java-sdk-s3:${Versions.aws}"
    const val aws_java_sdk_dynamodb = "com.amazonaws:aws-java-sdk-dynamodb:${Versions.aws}"
    const val aws_java_sdk_sns = "com.amazonaws:aws-java-sdk-sns:${Versions.aws}"
    const val aws_java_sdk_sqs = "com.amazonaws:aws-java-sdk-sqs:${Versions.aws}"
    const val aws_java_sdk_sts = "com.amazonaws:aws-java-sdk-sts:${Versions.aws}"
    const val aws_java_sdk_ec2 = "com.amazonaws:aws-java-sdk-ec2:${Versions.aws}"
    const val aws_java_sdk_test_utils = "com.amazonaws:aws-java-sdk-test-utils:${Versions.aws}"

    // HTTP
    const val async_http_client = "org.asynchttpclient:async-http-client:2.10.4"
    const val async_http_client_extras_retrofit2 = "org.asynchttpclient:async-http-client-extras-retrofit2:2.10.4"
    const val async_http_client_extras_rxjava2 = "org.asynchttpclient:async-http-client-extras-rxjava2:2.10.4"

    // Retrofit2
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2_adapter_java8 = "com.squareup.retrofit2:adapter-java8:${Versions.retrofit2}"
    const val retrofit2_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2}"
    const val retrofit2_converter_jackson = "com.squareup.retrofit2:converter-jackson:${Versions.retrofit2}"
    const val retrofit2_converter_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit2}"
    const val retrofit2_converter_protobuf = "com.squareup.retrofit2:converter-protobuf:${Versions.retrofit2}"
    const val retrofit2_converter_scalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit2}"
    const val retrofit2_mock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit2}"

    // https://github.com/JakeWharton/retrofit2-reactor-adapter/
    const val retrofit2_adapter_reactor = "com.jakewharton.retrofit:retrofit2-reactor-adapter:2.1.0"

    // OkHttp3
    const val okhttp3 = "com.squareup.okhttp3:okhttp:4.2.2"
    const val okhttp3_mockwebserver = "com.squareup.okhttp3:mockwebserver:4.2.2"

    // Moshi
    const val moshi = "com.squareup.moshi:moshi:1.9.1"
    const val moshi_adapters = "com.squareup.moshi:moshi-adapters:1.9.1"
    const val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:1.9.1"
    const val moshi_kotlin_codegen = "com.squareup.moshi:moshi-kotlin-codegen:1.9.1"

    const val fuel = "com.github.kittinunf.fuel:fuel:2.2.1"

    // ThreeTenBP
    const val threeten_bp = "org.threeten:threetenbp:1.4.0"

    const val zero_allocation_hashing = "net.openhft:zero-allocation-hashing:0.9"

    // Jackson
    const val jackson_annotations = "com.fasterxml.jackson.core:jackson-annotations:${Versions.jackson}"
    const val jackson_core = "com.fasterxml.jackson.core:jackson-core:${Versions.jackson}"
    const val jackson_databind = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"

    const val jackson_datatype_jsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}"
    const val jackson_datatype_jsr353 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr353:${Versions.jackson}"
    const val jackson_datatype_jdk8 = "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${Versions.jackson}"
    const val jackson_datatype_joda = "com.fasterxml.jackson.datatype:jackson-datatype-joda:${Versions.jackson}"
    const val jackson_datatype_guava = "com.fasterxml.jackson.datatype:jackson-datatype-guava:${Versions.jackson}"

    const val jackson_dataformat_avro = "com.fasterxml.jackson.dataformat:jackson-dataformat-avro:${Versions.jackson}"
    const val jackson_dataformat_protobuf = "com.fasterxml.jackson.dataformat:jackson-dataformat-protobuf:${Versions.jackson}"
    const val jackson_dataformat_csv = "com.fasterxml.jackson.dataformat:jackson-dataformat-csv:${Versions.jackson}"
    const val jackson_dataformat_properties = "com.fasterxml.jackson.dataformat:jackson-dataformat-properties:${Versions.jackson}"
    const val jackson_dataformat_yaml = "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Versions.jackson}"

    const val jackson_module_kotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}"
    const val jackson_module_paranamer = "com.fasterxml.jackson.module:jackson-module-paranamer:${Versions.jackson}"
    const val jackson_module_parameter_names = "com.fasterxml.jackson.module:jackson-module-parameter-names:${Versions.jackson}"
    const val jackson_module_scala = "com.fasterxml.jackson.module:jackson-module-scala:${Versions.jackson}"
    const val jackson_module_afterburner = "com.fasterxml.jackson.module:jackson-module-afterburner:${Versions.jackson}"

    const val gson = "com.google.code.gson:gson:2.8.5"
    const val gson_jodatime_serialisers = "com.fatboyindustrial.gson-jodatime-serialisers:gson-jodatime-serialisers:1.7.1"

    const val msgpack_core = "org.msgpack:msgpack-core:0.8.18"
    const val msgpack_jackson = "org.msgpack:jackson-dataformat-msgpack:0.8.18"

    const val protostuff_core = "io.protostuff:protostuff-core:1.6.0"
    const val protostuff_runtime = "io.protostuff:protostuff-runtime:1.6.0"
    const val protostuff_collectionschema = "io.protostuff:protostuff-collectionschema:1.6.0"

    // jasync-sql
    const val jasync_common = "com.github.jasync-sql:jasync-common:${Versions.jasync_sql}"
    const val jasync_mysql = "com.github.jasync-sql:jasync-mysql:${Versions.jasync_sql}"
    const val jasync_r2dbc_mysql = "com.github.jasync-sql:jasync-r2dbc-mysql:${Versions.jasync_sql}"
    const val jasync_postgresql = "com.github.jasync-sql:jasync-postgresql:${Versions.jasync_sql}"

    // Compression
    const val snappy_java = "org.xerial.snappy:snappy-java:1.1.7.3"
    const val lz4_java = "org.lz4:lz4-java:1.6.0"

    // Cryptography
    const val jasypt = "org.jasypt:jasypt:1.9.3"
    const val bouncycastle_bcprov = "org.bouncycastle:bcprov-jdk15on:1.64"
    const val bouncycastle_bcpkix = "org.bouncycastle:bcpkix-jdk15on:1.64"

    // Reactor
    const val reactor_core = "io.projectreactor:reactor-core:${Versions.reactor}"
    const val reactor_test = "io.projectreactor:reactor-test:${Versions.reactor}"
    const val reactor_netty = "io.projectreactor.netty:reactor-netty:0.8.9.RELEASE"

    // RxJava
    const val rxjava = "io.reactivex:rxjava:1.3.8"
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:2.2.10"
    const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"

    // Metrics
    const val metrics_annotation = "io.dropwizard.metrics:metrics-annotation:${Versions.metrics}"
    const val metrics_core = "io.dropwizard.metrics:metrics-core:${Versions.metrics}"
    const val metrics_json = "io.dropwizard.metrics:metrics-json:${Versions.metrics}"
    const val metrics_jvm = "io.dropwizard.metrics:metrics-jvm:${Versions.metrics}"
    const val metrics_graphite = "io.dropwizard.metrics:metrics-graphite:${Versions.metrics}"
    const val metrics_healthchecks = "io.dropwizard.metrics:metrics-healthchecks:${Versions.metrics}"
    const val metrics_jcache = "io.dropwizard.metrics:metrics-jcache:${Versions.metrics}"
    const val metrics_jmx = "io.dropwizard.metrics:metrics-jmx:${Versions.metrics}"


    const val micrometer_core = "io.micrometer:micrometer-core:${Versions.micrometer}"
    const val micrometer_test = "io.micrometer:micrometer-test:${Versions.micrometer}"
    const val micrometer_registry = "io.micrometer:micrometer-registry:${Versions.micrometer}"
    const val micrometer_registry_prometheus = "io.micrometer:micrometer-registry-prometheus:${Versions.micrometer}"
    const val micrometer_registry_graphite = "io.micrometer:micrometer-registry-graphite:${Versions.micrometer}"
    const val micrometer_registry_jmx = "io.micrometer:micrometer-registry-jmx:${Versions.micrometer}"

    const val latencyUtils = "org.latencyutils:LatencyUtils:2.0.3"
    const val hdrHistogram = "org.hdrhistogram:HdrHistogram:2.1.11"

    const val random_beans: String = "io.github.benas:random-beans:${Versions.random_beans}"
    const val reflectasm: String = "com.esotericsoftware:reflectasm:${Versions.reflectasm}"

    // MongoDB
    const val mongo_java_driver = "org.mongodb:mongo-java-driver:${Versions.mongo_driver}"
    const val mongo_bson = "org.mongodb:bson:${Versions.mongo_driver}"
    const val mongo_driver = "org.mongodb:mongodb-driver:${Versions.mongo_driver}"
    const val mongo_driver_async = "org.mongodb:mongodb-driver-async:${Versions.mongo_driver}"
    const val mongo_driver_core = "org.mongodb:mongodb-driver-core:${Versions.mongo_driver}"
    const val mongo_driver_reactivestreams = "org.mongodb:mongodb-driver-reactivestreams:1.11.0"

    // ArangoDB
    const val arangodb_java_driver = "com.arangodb:arangodb-java-driver:6.5.0"
    const val arangodb_java_driver_async = "com.arangodb:arangodb-java-driver:6.0.0"
    const val arangodb_spring_data = "com.arangodb:arangodb-spring-data:3.2.3"

    // Redis
    const val lettuce_core = "io.lettuce:lettuce-core:${Versions.lettuce}"
    const val redisson = "org.redisson:redisson:${Versions.redisson}"
    const val redisson_spring_boot_starter = "org.redisson:redisson-spring-boot-starter:${Versions.redisson}"
    const val redisson_spring_data_21 = "org.redisson:redisson-spring-data-21:${Versions.redisson}"
    const val redisson_spring_data_22 = "org.redisson:redisson-spring-data-22:${Versions.redisson}"

    // Memcached
    const val folsom = "com.spotify:folsom:1.6.1"
    const val spymemcached = "net.spy:spymemcached:2.12.3"

    // Cassandra
    const val cassandra_java_core = "com.datastax.oss:java-driver-core:${Versions.cassandra}"
    const val cassandra_java_query_builder = "com.datastax.oss:java-driver-query-builder:${Versions.cassandra}"
    const val cassandra_java_mapper_runtime = "com.datastax.oss:java-driver-mapper-runtime:${Versions.cassandra}"

    // ElasticSearch
    const val elasticsearch_rest_high_level_client = "org.elasticsearch.client:elasticsearch-rest-high-level-client:${Versions.elasticsearch}"
    const val elasticsearch_rest_client = "org.elasticsearch.client:elasticsearch-rest-client:${Versions.elasticsearch}"
    const val elasticsearch_rest_client_sniffer = "org.elasticsearch.client:elasticsearch-rest-client-sniffer:${Versions.elasticsearch}"

    // InfluxDB
    const val influxdb_java = "org.influxdb:influxdb-java:2.16"
    const val influxdb_spring_data = "com.github.miwurster:spring-data-influxdb:1.8"

    // RabbitMQ
    const val amqp_client = "com.rabbitmq:amqp-client:5.7.2"

    // Kafka
    const val kafka_clients = "org.apache.kafka:kafka-clients:2.3.0"
    const val pulsar_client = "org.apache.pulsar:pulsar-client:2.4.0"

    // Zipkin
    const val zipkin_brave = "io.zipkin.brave:brave:5.6.9"

    // Hibernate
    const val hibernate_core = "org.hibernate:hibernate-core:${Versions.hibernate}"
    const val hibernate_jcache = "org.hibernate:hibernate-jcache:${Versions.hibernate}"
    const val hibernate_testing = "org.hibernate:hibernate-testing:${Versions.hibernate}"
    const val hibernate_envers = "org.hibernate:hibernate-envers:${Versions.hibernate}"
    const val hibernate_jpa_2_1_api = "org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final"
    const val javassist = "org.javassist:javassist:3.25.0-GA"

    // Validators
    const val hibernate_validator = "org.hibernate:hibernate-validator:${Versions.hibernate_validator}"
    const val hibernate_validator_annotation_processor = "org.hibernate:hibernate-validator-annotation-processor:${Versions.hibernate_validator}"

    // Expression
    const val javax_el_api = "javax.el:javax.el-api:3.0.0"
    const val javax_el = "org.glassfish:javax.el:3.0.1-b11"

    const val querydsl_apt = "com.querydsl:querydsl-apt:${Versions.querydsl}"
    const val querydsl_jpa = "com.querydsl:querydsl-jpa:${Versions.querydsl}"

    const val hikaricp = "com.zaxxer:HikariCP:3.3.1"
    const val dbcp2 = "org.apache.commons:commons-dbcp2:2.6.0"  // latest : 2.6.0, vitamin used 2.1.1
    const val tomcat_jdbc = "org.apache.tomcat:tomcat-jdbc:9.0.21"

    const val mysql_connector_java = "mysql:mysql-connector-java:8.0.16"
    const val mariadb_java_client = "org.mariadb.jdbc:mariadb-java-client:2.4.2"
    const val postgresql_driver = "org.postgresql:postgresql:42.2.6"

    const val h2 = "com.h2database:h2:1.4.197"
    const val hsqldb = "org.hsqldb:hsqldb:2.5.0"
    const val flyway_core = "org.flywaydb:flyway-core:5.2.4"

    const val exposed = "org.jetbrains.exposed:exposed:0.16.2"

    const val requery = "io.requery:requery:1.6.0"
    const val requery_kotlin = "io.requery:requery-kotlin:1.6.0"
    const val requery_jackson = "io.requery:requery-jackson:1.6.0"
    const val requery_processor = "io.requery:requery-processor:1.6.0"

    const val jooq = "org.jooq:jooq:${Versions.jooq}"
    const val jooq_checker = "org.jooq:jooq-checker:${Versions.jooq}"
    const val jooq_meta = "org.jooq:jooq-meta:${Versions.jooq}"
    const val jooq_meta_extensions = "org.jooq:jooq-meta-extensions:${Versions.jooq}"
    const val jooq_codegen = "org.jooq:jooq-codegen:${Versions.jooq}"

    // UUID Generator
    const val java_uuid_generator = "com.fasterxml.uuid:java-uuid-generator:3.2.0"
    const val uuid_creator = "com.github.f4b6a3:uuid-creator:1.3.9"

    const val cache2k_all = "org.cache2k:cache2k-all:${Versions.cache2k}"
    const val cache2k_core = "org.cache2k:cache2k-core:${Versions.cache2k}"
    const val cache2k_spring = "org.cache2k:cache2k-spring:${Versions.cache2k}"
    const val cache2k_jcache = "org.cache2k:cache2k-jcache:${Versions.cache2k}"

    const val ehcache = "org.ehcache:ehcache:3.8.0"

    const val ignite_core = "org.apache.ignite:ignite-core:${Versions.ignite}"
    const val ignite_indexing = "org.apache.ignite:ignite-indexing:${Versions.ignite}"
    const val ignite_cassandra_store = "org.apache.ignite:ignite-cassandra-store:${Versions.ignite}"

    const val hazelcast = "com.hazelcast:hazelcast:${Versions.hazelcast}"
    const val hazelcast_all = "com.hazelcast:hazelcast-all:${Versions.hazelcast}"
    const val hazelcast_client = "com.hazelcast:hazelcast-client:${Versions.hazelcast}"
    const val hazelcast_spring = "com.hazelcast:hazelcast-spring:${Versions.hazelcast}"

    const val dagger = "com.google.dagger:dagger:2.23.1"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:2.23.1"

    const val koin_core = "org.koin:koin-core:${Versions.koin}"
    const val koin_core_ext = "org.koin:koin-core-ext:${Versions.koin}"
    const val koin_test = "org.koin:koin-test:${Versions.koin}"

    // CSV parsers
    const val univocity_parsers = "com.univocity:univocity-parsers:2.8.2"

    const val byte_buddy = "net.bytebuddy:byte-buddy:${Versions.byte_buddy}"
    const val byte_buddy_agent = "net.bytebuddy:byte-buddy-agent:${Versions.byte_buddy}"

    const val objenesis = "org.objenesis:objenesis:3.0.1"
    const val ow2_asm = "org.ow2.asm:asm:7.1"

    const val lombok = "org.projectlombok:lombok:1.18.10"

    // junit 5.4+ 부터는 junit-jupiter 만 있으면 됩니다.
    const val junit_jupiter = "org.junit.jupiter:junit-jupiter:${Versions.junit_jupiter}"
    const val junit_jupiter_api = "org.junit.jupiter:junit-jupiter-api:${Versions.junit_jupiter}"
    const val junit_jupiter_engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit_jupiter}"
    const val junit_jupiter_params = "org.junit.jupiter:junit-jupiter-params:${Versions.junit_jupiter}"
    const val junit_jupiter_migrationsupport = "org.junit.jupiter:junit-jupiter-migrationsupport:${Versions.junit_jupiter}"

    const val junit_platform_commons = "org.junit.platform:junit-platform-commons:${Versions.junit_platform}"
    const val junit_platform_engine = "org.junit.platform:junit-platform-engine:${Versions.junit_platform}"
    const val junit_platform_runner = "org.junit.platform:junit-platform-runner:${Versions.junit_platform}"
    const val junit_platform_launcher = "org.junit.platform:junit-platform-launcher:${Versions.junit_platform}"
    const val junit_platform_suite_api = "org.junit.platform:junit-platform-suite-api:${Versions.junit_platform}"

    const val kluent = "org.amshove.kluent:kluent:1.58"
    const val assertj_core: String = "org.assertj:assertj-core:3.12.2"
    const val mockk: String = "io.mockk:mockk:${Versions.mockk}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockito_junit_jupiter = "org.mockito:mockito-junit-jupiter:${Versions.mockito}"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"
    const val jmock_junit5 = "org.jmock:jmock-junit5:2.12.0"

    const val mockserver_netty = "org.mock-server:mockserver-netty:5.6.0"
    const val mockserver_client_java = "org.mock-server:mockserver-client-java:5.6.0"

    const val system_rules = "com.github.stefanbirkner:system-rules:1.19.0"

    const val jmh_core = "org.openjdk.jmh:jmh-core:1.21"
    const val jmh_generator_annprocess = "org.openjdk.jmh:jmh-generator-annprocess:1.21"

    const val testcontainers: String = "org.testcontainers:testcontainers:${Versions.testcontainers}"
    const val testcontainers_junit_jupiter: String = "org.testcontainers:junit-jupiter:${Versions.testcontainers}"

    const val testcontainers_cassandra: String = "org.testcontainers:cassandra:${Versions.testcontainers}"
    const val testcontainers_elasticsearch: String = "org.testcontainers:elasticsearch:${Versions.testcontainers}"
    const val testcontainers_influxdb: String = "org.testcontainers:influxdb:${Versions.testcontainers}"

    const val testcontainers_dynalite = "org.testcontainers:dynalite:${Versions.testcontainers}"

    const val testcontainers_mariadb: String = "org.testcontainers:mariadb:${Versions.testcontainers}"
    const val testcontainers_mysql: String = "org.testcontainers:mysql:${Versions.testcontainers}"
    const val testcontainers_postgresql: String = "org.testcontainers:postgresql:${Versions.testcontainers}"
    const val testcontainers_oracle_xe = "org.testcontainers:oracle-xe:${Versions.testcontainers}"

    const val testcontainers_kafka = "org.testcontainers:kafka:${Versions.testcontainers}"
    const val testcontainers_pulsar = "org.testcontainers:pulsar:${Versions.testcontainers}"

    // Twitter Text
    // 한글 분석을 위한 기본적인 통계 정보를 Twitter에서 제공합니다 
    const val twitter_text = "com.twitter.twittertext:twitter-text:3.0.1"
    const val open_korean_text = "org.openkoreantext:open-korean-text:2.3.1"

}