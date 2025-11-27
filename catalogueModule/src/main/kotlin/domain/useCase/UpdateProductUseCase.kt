package domain.useCase

import data.mapper.toEntity
import data.validator.productDTOValidator
import domain.repository.CatalogueRepository
import presentation.dto.ProductDTO

class UpdateProductUseCase(
    private val repo: CatalogueRepository
) {
    suspend operator fun invoke(dto: ProductDTO): Result<Unit> {
        val validation = productDTOValidator(dto)
        if (!validation.isValid) {
            return Result.failure(
                IllegalArgumentException("DTO is invalid")
            )
        }
        return try {
            repo.updateProduct(dto.toEntity())
            Result.success(Unit)

        } catch (e: IllegalArgumentException) {
            Result.failure(e)

        } catch (e: NoSuchElementException) {
            Result.failure(e)
        }
    }
}

