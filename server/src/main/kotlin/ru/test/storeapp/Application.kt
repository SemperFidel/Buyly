package ru.test.storeapp

import io.ktor.server.application.*
import io.ktor.server.cio.CIO
import io.ktor.server.engine.*
import ru.test.storeapp.config.configureAPI
import ru.test.storeapp.config.configureDI
import ru.test.storeapp.config.configureSerialization

fun main() {
    embeddedServer(CIO, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDI()
    configureAPI()
}