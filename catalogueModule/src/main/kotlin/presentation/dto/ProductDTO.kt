package presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    val id: String? = null,
    val name: String,
    val price: Double,
    val shortDesc: String,
    val imagesURL: List<String>
)
