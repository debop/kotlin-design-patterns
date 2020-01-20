dependencies {

    api(project(":kommons-core"))
    testApi(project(":kommons-junit-jupiter"))

    api(Libraries.testcontainers)

    // Jdbc Database
    implementation(Libraries.testcontainers_mysql)
    implementation(Libraries.testcontainers_mariadb)
    implementation(Libraries.testcontainers_postgresql)

    implementation(Libraries.hikaricp)

    testImplementation(Libraries.mysql_connector_java)
    testImplementation(Libraries.mariadb_java_client)
    testImplementation(Libraries.postgresql_driver)

    // Redis
    testImplementation(Libraries.redisson)
    testImplementation(Libraries.lettuce_core)

    testImplementation(Libraries.fst)
    testImplementation(Libraries.snappy_java)

    // Memcached
    implementation(Libraries.folsom)
    testImplementation(Libraries.spymemcached)

    // Apache Ignite
    testImplementation(Libraries.ignite_core)

    // Hazelcast
    implementation(Libraries.hazelcast_all)
    testImplementation(Libraries.hazelcast_client)

    // MongoDB
    testImplementation(Libraries.mongo_java_driver)

    // ArangoDB
    testImplementation(Libraries.arangodb_java_driver)
    testImplementation(Libraries.arangodb_java_driver_async)
    testImplementation(Libraries.arangodb_spring_data)

    // Message Queue
    implementation(Libraries.testcontainers_kafka)
    implementation(Libraries.testcontainers_pulsar)

    testImplementation(Libraries.amqp_client)
    testImplementation(Libraries.kafka_clients)
    testImplementation(Libraries.pulsar_client)

    testImplementation(Libraries.zipkin_brave)

    // testApi(project(":kotlinx-http"))
    testImplementation(Libraries.okhttp3)

    // Dynalite as a stand-in for AWS DynamoDB
    implementation(Libraries.testcontainers_dynalite)

    // Amazon SDK
    testImplementation(Libraries.aws_java_sdk_s3)
    testImplementation(Libraries.aws_java_sdk_dynamodb)

    // Cassandrra
    implementation(Libraries.testcontainers_cassandra)
    testImplementation(Libraries.cassandra_java_core)
    testImplementation(Libraries.cassandra_java_query_builder)
    testImplementation(Libraries.cassandra_java_mapper_runtime)

    testImplementation(Libraries.metrics_jmx)
    testImplementation(Libraries.netty_transport_native_epoll + ":linux-x86_64")
    testImplementation(Libraries.netty_transport_native_kqueue + ":osx-x86_64")

    // ElasticSearch
    implementation(Libraries.testcontainers_elasticsearch)
    testImplementation(Libraries.elasticsearch_rest_client)
    testImplementation(Libraries.elasticsearch_rest_high_level_client)

    // InfluxDB
    implementation(Libraries.testcontainers_influxdb)
    testImplementation(Libraries.influxdb_java)

    testImplementation(Libraries.kotlinx_coroutines_jdk8)
}