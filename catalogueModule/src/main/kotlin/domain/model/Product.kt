package domain.model

import kotlinx.serialization.SerialName
import org.bson.types.ObjectId

data class Product(
    @SerialName("_id")
    val id: ObjectId? = null,
    val name: String,
    val price: Double,
    val shortDesc: String,
    val imagesURL: List<String>,
)