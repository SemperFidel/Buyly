package presentation.service

import domain.useCase.CreateProductUseCase
import domain.useCase.DeleteProductUseCase
import domain.useCase.GetAllProductsUseCase
import domain.useCase.GetProductByIdUseCase
import domain.useCase.UpdateProductUseCase
import presentation.dto.ProductDTO

class CatalogueService(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
) {
    suspend fun getAll(): List<ProductDTO> = getAllProductsUseCase()
    suspend fun getById(id: String) = getProductByIdUseCase(id)
    suspend fun create(product: ProductDTO) = createProductUseCase(product)
    suspend fun update(product: ProductDTO) = updateProductUseCase(product)
    suspend fun delete(id: String) = deleteProductUseCase(id)
}