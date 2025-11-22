package ru.test.storeapp.config

import io.ktor.server.application.Application
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing
import presentation.controller.catalogueController

fun Application.configureAPI() {
    routing {
        openAPI(path = "openapi", swaggerFile = "openapi/generated.json")
        swaggerUI(path = "swagger", swaggerFile = "openapi/generated.json")

        catalogueController()
    }
}