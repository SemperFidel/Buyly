package presentation.service

import domain.useCase.CreateProductUseCase
import domain.useCase.DeleteProductUseCase
import domain.useCase.GetAllProductsUseCase
import domain.useCase.GetProductByIdUseCase
import domain.useCase.UpdateProductUseCase
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import presentation.dto.ProductDTO

class CatalogueService(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
) {

    suspend fun getAll(): List<ProductDTO> =
        getAllProductsUseCase()

    suspend fun getById(id: String): ProductDTO =
        getProductByIdUseCase(id)
            ?: throw NotFoundException("Product with id=$id not found")

    suspend fun create(product: ProductDTO): Unit =
        try {
            createProductUseCase(product)
        } catch (e: IllegalArgumentException) {
            throw BadRequestException(e.message ?: "Invalid product data")
        }

    suspend fun update(product: ProductDTO): Unit =
        try {
            updateProductUseCase(product)
        } catch (e: IllegalArgumentException) {
            throw BadRequestException(e.message ?: "Invalid product data")
        }

    suspend fun delete(id: String): Unit =
        try {
            deleteProductUseCase(id)
        } catch (e: IllegalArgumentException) {
            throw BadRequestException(e.message ?: "Invalid id")
        }
}