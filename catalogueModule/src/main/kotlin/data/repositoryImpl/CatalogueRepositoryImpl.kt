package data.repositoryImpl

import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoCollection
import data.datasource.MongoConnection
import data.validator.productValidator
import data.validator.validateObjectId
import domain.model.Product
import domain.repository.CatalogueRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.bson.types.ObjectId

class CatalogueRepositoryImpl(
    private val db: MongoConnection,
): CatalogueRepository {
    private val collection: MongoCollection<Product> =
        db.database.getCollection("products", Product::class.java)

    override suspend fun getAllProducts(): List<Product> =
        collection.find().asFlow().toList()

    override suspend fun getProductById(id: String): Product? {
        val objId = validateObjectId(id)
        return collection.find(Filters.eq("_id", objId)).awaitFirstOrNull()
    }

    override suspend fun createProduct(product: Product) {
        val insertValue = productValidator(product) //Validator added
        if (!insertValue.isValid) {
            throw IllegalArgumentException("Product is invalid")
        }
        collection.insertOne(product).awaitSingle()
    }

    override suspend fun updateProduct(product: Product) {
        val id = requireNotNull(product.id) { "Product ID must not be null for update" }

        val result = productValidator(product)
        if (!result.isValid) {
            throw IllegalArgumentException("Product validation failed")
        }

        val updateResult = collection
            .replaceOne(Filters.eq("_id", id), product)
            .awaitSingle()

        if (updateResult.matchedCount == 0L) {
            throw NoSuchElementException("Product with id '$id' not found")
        }
    }

    override suspend fun deleteProductById(id: String) {
        val objId = validateObjectId(id)
        val deleteResult = collection.deleteOne(Filters.eq("_id", objId)).awaitSingle()

        if (deleteResult.deletedCount == 0L) {
            throw NoSuchElementException("Product with id '$id' not found")
        }
    }
}