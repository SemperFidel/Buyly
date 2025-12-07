package data.datasource

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoDatabase

interface MongoConnection {
    val client: MongoClient
    val database: MongoDatabase
}
