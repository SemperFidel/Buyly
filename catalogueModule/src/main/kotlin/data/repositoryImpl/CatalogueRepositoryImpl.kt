package data.repositoryImpl

import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoCollection
import data.datasource.MongoConnection
import domain.model.Product
import domain.repository.CatalogueRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle

class CatalogueRepositoryImpl(
    private val db: MongoConnection,
): CatalogueRepository {
    private val collection: MongoCollection<Product> =
        db.database.getCollection("products", Product::class.java)

    override suspend fun getAllProducts(): List<Product> =
        collection.find().asFlow().toList()

    override suspend fun getProductById(id: String): Product? =
        collection.find(Filters.eq("_id", id)).awaitFirstOrNull()

    override suspend fun createProduct(product: Product) {
        collection.insertOne(product).awaitSingle()
    }

    override suspend fun updateProduct(product: Product) {
        requireNotNull(product.id) { "Product ID must not be null for update" }
        collection.replaceOne(Filters.eq("_id", product.id), product).awaitSingle()
    }

    override suspend fun deleteProductById(id: String) {
        collection.deleteOne(Filters.eq("_id", id)).awaitSingle()
    }
}