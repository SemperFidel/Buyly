package domain.useCase

import domain.repository.CatalogueRepository

class DeleteProductUseCase(
    private val repo: CatalogueRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return try {
            repo.deleteProductById(id)
            Result.success(Unit)

        } catch (e: IllegalArgumentException) {
            Result.failure(e)

        } catch (e: NoSuchElementException) {
            Result.failure(e)
        }
    }
}
