package domain.useCase

import domain.repository.CatalogueRepository

class DeleteProductUseCase(
    private val repo: CatalogueRepository
) {
    suspend operator fun invoke(id: String) = repo.deleteProductById(id)
}