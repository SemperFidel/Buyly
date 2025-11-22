package domain.useCase

import data.mapper.toEntity
import domain.repository.CatalogueRepository
import presentation.dto.ProductDTO

class CreateProductUseCase(
    private val repo: CatalogueRepository
) {
    suspend operator fun invoke(product: ProductDTO) = repo.createProduct(product.toEntity())
}