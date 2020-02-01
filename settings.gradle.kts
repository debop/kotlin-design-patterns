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
include("data-locality")
include("data-mapper")
include("data-transfer-object")
include("decorator")
include("delegation")
include("dirty-flag")
include("double-buffer")

include("event-aggregator")
include("event-asynchronous")
include("event-driven-architecture")
include("event-queue")
include("event-sourcing")
include("execute-around")
include("extension-objects")

include("facade")
include("factory-kit")
include("factory-method")
include("feature-toggle")

include("guarded-suspension")

include("interpreter")

include("lazy-loading")

include("retry")
include("role-object")

include("poison-pill")
include("producer-consumer")
include("promise")

include("queue-load-leveling")

include("resource-acquisition-is-initialization")

include("semaphore")
include("servant")
include("sharding")
include("singleton")
include("specification")
include("state")
include("step-builder")
include("strategy")
include("subclass-sandbox")

include("template-method")
include("thread-pool")
include("throttling")
include("tolerant-reader")

include("update-method")

include("visitor")
