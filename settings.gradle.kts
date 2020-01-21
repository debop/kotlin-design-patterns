pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
        mavenCentral()
        // for Avro plugin
        maven(url = "https://dl.bintray.com/gradle/gradle-plugins")
    }
}

rootProject.name = "kotlin-design-patterns"

include("kommons-logging")
include("kommons-core")
include("kommons-junit-jupiter")
include("kommons-testcontainers")

include("abstract-document")
include("abstract-factory")
include("acycle-visitor")
include("adaptor")
include("ambassador")

include("api-gateway")
include("api-gateway:api-gateway-service")
include("api-gateway:image-microservice")
include("api-gateway:price-microservice")

include("async-method-invocation")

include("balking")
include("bridge")
include("builder")
include("business-delegate")

include("caching")
include("callback")
include("chain")
include("collection-pipeline")
include("command")
include("composite")
include("converter")
include("cqrs")

include("data-bus")
include("data-mapper")
include("data-transfer-object")
include("decorator")
include("delegation")
include("dirty-flag")

include("event-aggregator")
include("event-asynchronous")
include("event-sourcing")
include("execute-around")

include("factory-method")

include("guarded-suspension")

include("lazy-loading")

include("producer-consumer")

include("singleton")
include("state")
include("strategy")

include("template-method")
include("throttling")

include("visitor")
