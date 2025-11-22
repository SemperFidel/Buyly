package presentation.controller

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
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
        get{
            val res = service.getAll()
            call.respond(res)
        }

        get("/{id}") {
            val id = call.parameters["id"]
                ?: return@get call.respondText(
                    "Missing id",
                    status = HttpStatusCode.BadRequest
                )

            val res = service.getById(id)
                ?: return@get call.respondText(
                    "Not found",
                    status = HttpStatusCode.NotFound
                )

            return@get call.respond(res)
        }

        post {
            val product = try {
                call.receive<ProductDTO>()
            } catch (ex: Exception) {
                return@post call.respondText(
                    "Invalid JSON",
                    status = HttpStatusCode.BadRequest
                )
            }

            try {
                service.create(product)
                return@post call.respondText("Created", status = HttpStatusCode.Created)
            } catch (ex: Exception) {
                return@post call.respondText(
                    "Bad request",
                    status = HttpStatusCode.BadRequest
                )
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]
                ?: return@put call.respondText("Missing id",
                    status = HttpStatusCode.BadRequest)

            val dto = try {
                call.receive<ProductDTO>()
            } catch (e: Exception) {
                return@put call.respondText("Invalid JSON",
                    status = HttpStatusCode.BadRequest)
            }

            val existing = service.getById(id)
                ?: return@put call.respondText("Not found",
                    status = HttpStatusCode.NotFound)

            val updatedDto = dto.copy(id = id)

            try {
                service.update(updatedDto)
                return@put call.respondText("Updated",
                    status = HttpStatusCode.OK)
            } catch (e: Exception) {
                return@put call.respondText("Bad request",
                    status = HttpStatusCode.BadRequest)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]
                ?: return@delete call.respondText("Missing id",
                    status = HttpStatusCode.BadRequest)

            val existing = service.getById(id)
                ?: return@delete call.respondText("Not found",
                    status = HttpStatusCode.NotFound)

            try {
                service.delete(id)
                return@delete call.respondText("Deleted",
                    status = HttpStatusCode.OK)
            } catch (e: Exception) {
                return@delete call.respondText("Bad request",
                    status =HttpStatusCode.BadRequest)
            }
        }
    }
}