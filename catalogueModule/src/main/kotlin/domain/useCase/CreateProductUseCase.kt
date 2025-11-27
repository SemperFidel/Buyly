package domain.useCase

import data.mapper.toEntity
import data.validator.productDTOValidator
import domain.repository.CatalogueRepository
import presentation.dto.ProductDTO

class CreateProductUseCase(
    private val repo: CatalogueRepository
) {
    suspend operator fun invoke(dto: ProductDTO): Result<Unit> {
        val result = productDTOValidator(dto)
        if (!result.isValid) {
            return Result.failure(
                IllegalArgumentException("ProductDTO ${dto.id} is invalid.")
            )
        }
        return repo.createProduct(dto.toEntity()).let { Result.success(Unit) }
    }
}