package data.mapper

import domain.model.Product
import org.bson.types.ObjectId
import presentation.dto.ProductDTO

fun Product.toDTO() = ProductDTO(
    id = id?.toHexString(),
    name = name,
    price = price,
    shortDesc = shortDesc,
    imagesURL = imagesURL
)

fun ProductDTO.toEntity() = Product(
    id = id?.let { ObjectId(it) },
    name = name,
    price = price,
    shortDesc = shortDesc,
    imagesURL = imagesURL
)