package unitTest

import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import data.datasource.MongoConnection
import data.repositoryImpl.CatalogueRepositoryImpl
import domain.model.Product
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.bson.BsonObjectId
import org.bson.types.ObjectId

class CatalogueRepositoryImplTest : FunSpec({

    val db = mockk<MongoConnection>()
    val database = mockk<MongoDatabase>()
    val collection = mockk<MongoCollection<Product>>(relaxed = true)

    lateinit var repo: CatalogueRepositoryImpl

    beforeTest {
        every { db.database } returns database
        every { database.getCollection("products", Product::class.java) } returns collection

        repo = CatalogueRepositoryImpl(db)
    }

    test("getAllProducts returns all products") {
        val expected = Product(
            id = ObjectId(),
            name = "Coffee",
            price = 12.5,
            shortDesc = "Fresh coffee",
            imagesURL = listOf("img1.png")
        )

        every { collection.find() } returns mockk()
        every { collection.find().asFlow() } returns flowOf(expected)

        val result = repo.getAllProducts()

        result shouldBe listOf(expected)
    }

    test("getProductById returns product when exists") {
        val id = ObjectId()
        val hex = id.toHexString()

        val expected = Product(
            id = id,
            name = "Tea",
            price = 10.0,
            shortDesc = "Green tea",
            imagesURL = emptyList()
        )

        coEvery { collection.find(Filters.eq("_id", id)) } returns mockk()
        coEvery { collection.find(Filters.eq("_id", id)).awaitFirstOrNull() } returns expected

        repo.getProductById(hex) shouldBe expected
    }

    test("createProduct inserts product when valid") {
        val product = Product(
            name = "AAA",
            price = 10.0,
            shortDesc = "Valid",
            imagesURL = emptyList()
        )

        coEvery {
            collection.insertOne(any()).awaitSingle()
        } returns InsertOneResult.acknowledged(
            BsonObjectId(ObjectId.get())
        )

        repo.createProduct(product)

        coVerify { collection.insertOne(any()) }
    }

    test("createProduct throws on invalid product") {
        val invalid = Product(
            name = "",
            price = -10.0,
            shortDesc = "",
            imagesURL = emptyList()
        )

        shouldThrow<IllegalArgumentException> {
            repo.createProduct(invalid)
        }
    }

    test("updateProduct updates existing product") {
        val product = Product(
            id = ObjectId(),
            name = "Updated",
            price = 20.0,
            shortDesc = "Updated desc",
            imagesURL = listOf("u.png")
        )

        every { collection.replaceOne(any(), any()) } returns mockk()
        coEvery {
            collection.replaceOne(any(), any()).awaitSingle()
        } returns UpdateResult.acknowledged(1, 1, null)

        repo.updateProduct(product)
    }

    test("updateProduct throws if product not found") {
        val product = Product(
            id = ObjectId(),
            name = "Ghost",
            price = 10.0,
            shortDesc = "No such item",
            imagesURL = emptyList()
        )

        every { collection.replaceOne(any(), any()) } returns mockk()
        coEvery {
            collection.replaceOne(any(), any()).awaitSingle()
        } returns UpdateResult.acknowledged(0, 0, null)

        shouldThrow<NoSuchElementException> {
            repo.updateProduct(product)
        }
    }

    test("deleteProductById deletes when exists") {
        val id = ObjectId()
        val hex = id.toHexString()

        every { collection.deleteOne(any()) } returns mockk()
        coEvery { collection.deleteOne(any()).awaitSingle() } returns DeleteResult.acknowledged(1)

        repo.deleteProductById(hex)
    }

    test("deleteProductById throws when product not found") {
        val id = ObjectId().toHexString()

        every { collection.deleteOne(any()) } returns mockk()
        coEvery { collection.deleteOne(any()).awaitSingle() } returns DeleteResult.acknowledged(0)

        shouldThrow<NoSuchElementException> {
            repo.deleteProductById(id)
        }
    }
})