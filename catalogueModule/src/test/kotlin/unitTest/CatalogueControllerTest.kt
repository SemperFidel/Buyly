package unitTest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import presentation.controller.catalogueController
import presentation.dto.ProductDTO
import presentation.service.CatalogueService

class CatalogueControllerTest : StringSpec({
    val mockService = mockk<CatalogueService>()

    fun Application.testModule() {
        install(Koin) {
            modules(
                module {
                    single { mockService }
                }
            )
        }
        install(ContentNegotiation) {
            json()
        }
        routing {
            catalogueController()
        }
    }

    "GET /catalogue returns list of products" {
        val sampleProduct = ProductDTO(
            id = "1",
            name = "Phone",
            price = 1000.0,
            shortDesc = "Smartphone",
            imagesURL = listOf("url1", "url2")
        )

        coEvery { mockService.getAll() } returns listOf(sampleProduct)

        testApplication {
            application {
                testModule()
            }

            val response = client.get("/catalogue")

            response.status shouldBe HttpStatusCode.Companion.OK
            response.bodyAsText() shouldBe
                    """[{"id":"1","name":"Phone","price":1000.0,"shortDesc":"Smartphone","imagesURL":["url1","url2"]}]"""
        }
    }

    "GET /catalogue/{id} returns product" {
        val sampleProduct = ProductDTO(
            id = "1",
            name = "Phone",
            price = 1000.0,
            shortDesc = "Smartphone",
            imagesURL = listOf("url1", "url2")
        )

        coEvery { mockService.getById("1") } returns sampleProduct

        testApplication {
            application { testModule() }

            val response = client.get("/catalogue/1")

            response.status shouldBe HttpStatusCode.Companion.OK
            response.body<ProductDTO>() shouldBe sampleProduct
        }
    }

    "POST /catalogue creates product" {
        coEvery { mockService.create(any()) } returns Unit

        val dtoToCreate = ProductDTO(
            name = "New",
            price = 200.0,
            shortDesc = "New product",
            imagesURL = listOf("img1", "img2")
        )

        testApplication {
            application { testModule() }

            val response = client.post("/catalogue") {
                contentType(ContentType.Application.Json)
                setBody(dtoToCreate)
            }

            response.status shouldBe HttpStatusCode.Companion.Created

            coVerify { mockService.create(dtoToCreate) }
        }
    }

    "PUT /catalogue/{id} updates product" {
        coEvery { mockService.update(any()) } returns Unit

        val updatedDto = ProductDTO(
            name = "Updated",
            price = 500.0,
            shortDesc = "Updated product",
            imagesURL = listOf("img3")
        )

        testApplication {
            application { testModule() }

            val response = client.put("/catalogue/10") {
                contentType(ContentType.Application.Json)
                setBody(updatedDto)
            }

            response.status shouldBe HttpStatusCode.Companion.OK

            coVerify { mockService.update(updatedDto.copy(id = "10")) }
        }
    }

    "DELETE /catalogue/{id} deletes product" {
        coEvery { mockService.delete("3") } returns Unit

        testApplication {
            application { testModule() }

            val response = client.delete("/catalogue/3")

            response.status shouldBe HttpStatusCode.Companion.OK
            coVerify { mockService.delete("3") }
        }
    }
})