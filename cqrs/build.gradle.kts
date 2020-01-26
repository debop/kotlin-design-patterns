plugins {
    idea
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("kapt")
}

allOpen {
    annotation("javax.persistence.Entity")
}

noArg {
    annotation("javax.persistence.Entity")
    invokeInitializers = true
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.plus(kaptMain)
        generatedSourceDirs.plus(kaptMain)

        val kaptTest = file("build/generated/source/kapt/test")
        testSourceDirs.plus(kaptTest)
    }
}

dependencies {

    api(project(":kommons-core"))

    api(Libraries.hibernate_core)
    api(Libraries.hibernate_jpa_2_1_api)

    api(Libraries.querydsl_jpa)

    // Java 9+ 에서 kapt에 버그가 있습니다. 우선 QueryDSL을 사용하지 않으므로, 막았습니다.
    //    kapt(Libraries.querydsl_apt + ":jpa")
    //    kaptTest(Libraries.querydsl_apt + ":jpa")

    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.data:spring-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation(Libraries.h2)

    testImplementation("org.springframework.boot:spring-boot-starter-test")

}