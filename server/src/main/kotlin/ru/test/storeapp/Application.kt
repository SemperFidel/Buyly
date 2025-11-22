package ru.test.storeapp

import io.ktor.server.application.*
import io.ktor.server.cio.CIO
import io.ktor.server.config.yaml.YamlConfigLoader
import io.ktor.server.engine.*
import ru.test.storeapp.config.configureAPI
import ru.test.storeapp.config.configureDI
import ru.test.storeapp.config.configureSerialization

fun main() {
    val yamlConfig = YamlConfigLoader().load("application.yaml")
        ?: throw IllegalStateException("Cannot load YAML config")

    embeddedServer(
        CIO,
        host = yamlConfig.property("ktor.deployment.host").getString(),
        port = yamlConfig.property("ktor.deployment.port").getString().toInt()
    ) { module() }.start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDI()
    configureAPI()
}