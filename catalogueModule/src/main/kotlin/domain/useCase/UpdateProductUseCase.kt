package domain.useCase

import data.mapper.toEntity
import domain.repository.CatalogueRepository
import presentation.dto.ProductDTO

class UpdateProductUseCase(
    private val repo: CatalogueRepository
) {
    suspend operator fun invoke(product: ProductDTO) = repo.updateProduct(product.toEntity())
}