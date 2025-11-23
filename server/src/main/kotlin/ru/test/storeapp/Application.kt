package ru.test.storeapp

import io.ktor.server.application.*
import ru.test.storeapp.config.configureAPI
import ru.test.storeapp.config.configureDI
import ru.test.storeapp.config.configureSerialization
import ru.test.storeapp.config.configureStatusPages

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureDI()
    configureAPI()
    configureStatusPages()
}