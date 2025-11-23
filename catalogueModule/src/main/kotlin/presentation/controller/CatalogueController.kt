package presentation.controller

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.MissingRequestParameterException
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import org.koin.ktor.ext.get
import presentation.dto.ProductDTO
import presentation.service.CatalogueService

fun Route.catalogueController(service: CatalogueService = get()) {
    route("/catalogue") {
        openAPI(path = "/docs/openapi", swaggerFile = "openapi/api-doc.json")
        swaggerUI(path = "/docs/swagger", swaggerFile = "openapi/api-doc.json")
        get{
            val res = service.getAll()
            call.respond(res)
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MissingRequestParameterException("id")
            call.respond(service.getById(id))
        }

        post {
            val dto = call.receive<ProductDTO>()
            service.create(dto)
            call.respond(HttpStatusCode.Created)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw MissingRequestParameterException("id")
            val dto = call.receive<ProductDTO>().copy(id = id)
            service.update(dto)
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw MissingRequestParameterException("id")
            service.delete(id)
            call.respond(HttpStatusCode.OK)
        }


    }
}