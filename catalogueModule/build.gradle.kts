plugins {
    kotlin("jvm") version "2.2.20"
    alias(libs.plugins.kotlinPluginSerialization)
    alias(libs.plugins.gatling)
}

group = "ru.test"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinxCoroutines)
    implementation(libs.kotlinxCoroutinesReactive)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.openapi)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinxSerialization)

    implementation(libs.koin.core)
    implementation(libs.koin.ktor)

    implementation(libs.konform)

    implementation(libs.mongodb.bson.kotlinx)
    implementation(libs.mongodb.driver.reactivestreams)
    implementation(libs.mongodb.driver.kotlin.coroutine)
    implementation(libs.mongodb.driver.kotlin.extensions)

    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.framework.engine)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.ktor)
    testImplementation(libs.kotest.extensions.koin)
    testImplementation(libs.mockk)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(kotlin("test"))

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}