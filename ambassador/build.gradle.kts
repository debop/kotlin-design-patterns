dependencies {
    api(project(":kommons-logging"))
    testApi(project(":kommons-junit-jupiter"))

    api(Libraries.resilience4j_retry)
}