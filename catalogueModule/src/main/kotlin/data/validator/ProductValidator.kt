package data.validator

import domain.model.Product
import io.konform.validation.Validation
import io.konform.validation.constraints.minItems
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.minimum
import io.konform.validation.constraints.pattern
import io.konform.validation.onEach
import presentation.dto.ProductDTO

private val imageUrlRegex = Regex(
    "[-a-zA-Z0-9@:%_+.~#?&/=]{2,256}\\.[a-z]{2,4}(/[-a-zA-Z0-9@:%_+.~#?&/=]*)?"
)
val productDTOValidator = Validation {
    ProductDTO::name {
        minLength(1) hint "Name must be at least 1 character long"
        constrain("Name cannot be blank") { it.isNotBlank() }
    }
    ProductDTO::price {
        minimum(0) hint "Price cannot be negative"
    }
    ProductDTO::shortDesc {
        minLength(1) hint "Description must be at least 1 character long"
        constrain("Description cannot be blank") { it.isNotBlank() }
    }
    ProductDTO::imagesURL {
        minItems(1) hint "At least one image must be provided"
        onEach {
            pattern(imageUrlRegex) hint "Image URL is invalid"
        }
    }
}
val productValidator = Validation {
    Product::name {
        minLength(1) hint "Name must be at least 1 character long"
        constrain("Name cannot be blank") { it.isNotBlank() }
    }
    Product::price {
        minimum(0) hint "Price cannot be negative"
    }
    Product::shortDesc {
        minLength(1) hint "Description must be at least 1 character long"
    }
    Product::imagesURL {
        minItems(1) hint "At least one image must be provided"
        onEach {
            pattern(imageUrlRegex) hint "Image URL is invalid"
        }
    }
}
