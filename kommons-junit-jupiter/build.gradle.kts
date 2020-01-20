dependencies {

    api(project(":kommons-logging"))

    api(Libraries.kotlin_test)
    api(Libraries.kotlin_test_junit5)

    api(Libraries.junit_jupiter)
    api(Libraries.junit_jupiter_engine)

    api(Libraries.junit_platform_launcher)
    api(Libraries.junit_platform_runner)

    api(Libraries.mockk)
    api(Libraries.kluent)

    api(Libraries.slf4j_api)
    api(Libraries.logback)

    api(Libraries.mockserver_netty)
    api(Libraries.mockserver_client_java)
    testApi(Libraries.async_http_client)

    // For property based testing
    api(Libraries.random_beans)

    api(Libraries.kotlinx_coroutines_jdk8)
    api(Libraries.kotlinx_coroutines_debug)
    api(Libraries.kotlinx_coroutines_test)

}