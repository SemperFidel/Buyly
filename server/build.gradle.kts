@file:OptIn(OpenApiPreview::class)

import io.ktor.plugin.OpenApiPreview

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "ru.test.storeapp"
version = "1.0.0"
application {
    mainClass.set("ru.test.storeapp.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":catalogueModule"))
    implementation(libs.logback)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.openapi)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    testImplementation(libs.ktor.serverTestHost)
}

ktor {
    openApi{
        title = "Buyly API"
        version = "1.0.0"
        target = project.layout.projectDirectory.file("openapi/generated.json")
    }
}