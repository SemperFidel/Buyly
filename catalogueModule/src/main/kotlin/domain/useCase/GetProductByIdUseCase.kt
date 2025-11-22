package domain.useCase

import data.mapper.toDTO
import domain.repository.CatalogueRepository
import presentation.dto.ProductDTO

class GetProductByIdUseCase(
    private val repo: CatalogueRepository
) {
    suspend operator fun invoke(id: String): ProductDTO? = repo.getProductById(id)?.toDTO()
}