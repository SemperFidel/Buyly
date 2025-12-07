package data.datasource

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase

class MongoConnectionImpl(
    connectionString: String,
    dbName: String
): MongoConnection {
    override val client: MongoClient
    override val database: MongoDatabase

    init {
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .build()

        client = MongoClients.create(settings)
        database = client.getDatabase(dbName)
    }
}