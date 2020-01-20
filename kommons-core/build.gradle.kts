plugins {
    // test 코드를 다른 모듈에서 참조할 수 있도록 해줍니다
    // see: https://github.com/hauner/gradle-plugins/tree/master/jartest
    id(BuildPlugins.jarTest) version BuildPlugins.Versions.jarTest
}

dependencies {

    api(project(":kommons-logging"))
    testApi(project(":kommons-junit-jupiter"))

    // Coroutines  
    implementation(Libraries.kotlinx_coroutines_jdk8)

    api(Libraries.validation_api)

    // Apache Commons
    api(Libraries.commons_lang3)
    implementation(Libraries.commons_codec)
    implementation(Libraries.commons_text)

    // Cache2k
    implementation(Libraries.cache2k_core)
}