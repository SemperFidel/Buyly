package domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Product(
    @SerialName("_id")
    @Contextual val id: ObjectId? = null,
    val name: String,
    val price: Double,
    val shortDesc: String,
    val imagesURL: List<String>,
)