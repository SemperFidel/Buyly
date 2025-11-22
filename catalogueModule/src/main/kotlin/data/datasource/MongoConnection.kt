package data.datasource

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase

class MongoConnection(
    connectionString: String,
    dbName: String
) {
    val client: MongoClient
    val database: MongoDatabase

    init {
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .build()

        client = MongoClients.create(settings)
        database = client.getDatabase(dbName)
    }
}