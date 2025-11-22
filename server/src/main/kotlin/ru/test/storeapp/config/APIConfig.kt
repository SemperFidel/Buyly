package ru.test.storeapp.config

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import presentation.controller.catalogueController

fun Application.configureAPI() {
    routing {
        catalogueController()
    }
}