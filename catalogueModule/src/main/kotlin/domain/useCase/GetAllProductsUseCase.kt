package domain.useCase

import data.mapper.toDTO
import domain.repository.CatalogueRepository
import presentation.dto.ProductDTO

class GetAllProductsUseCase(
    private val repo: CatalogueRepository
) {
    suspend operator fun invoke(): List<ProductDTO> = repo.getAllProducts().map { it.toDTO() }
}