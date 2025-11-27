package data.validator

import org.bson.types.ObjectId

fun validateObjectId(id: String): ObjectId {
    try {
        return ObjectId(id)
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Invalid ObjectId format: '$id'")
    }
}