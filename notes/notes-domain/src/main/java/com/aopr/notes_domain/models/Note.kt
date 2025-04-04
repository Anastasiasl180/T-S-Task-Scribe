package com.aopr.notes_domain.models

data class Note(
    val id:Int,
    val tittle:String,
    val description:String,
    val date:String ="",
    val isPinned:Boolean = false
)


fun Note.toFirestoreMap(): Map<String, Any?> {
    return mapOf(
        "id" to id,
        "tittle" to tittle,
        "description" to description,
        "date" to date,
        "isPinned" to isPinned
    )
}

fun noteFromFirestore(data: Map<String, Any?>): Note {
    return Note(
        id = (data["id"] as? Long)?.toInt() ?: 0,
        tittle = data["tittle"] as? String ?: "",
        description = data["description"] as? String ?: "",
        date = data["date"] as? String ?: "",
        isPinned = data["isPinned"] as? Boolean ?: false
    )
}


   /* fun toFirestore(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "tittle" to tittle,
            "description" to description,
            "date" to date,
            "isPinned" to isPinned
        )
    }

    companion object {
        fun fromFirestore(data: Map<String, Any?>): Note {
            return Note(
                id = (data["id"] as? Long)?.toInt() ?: 0,
                tittle = data["tittle"] as? String ?: "",
                description = data["description"] as? String ?: "",
                date = data["date"] as? String ?: "",
                isPinned = data["isPinned"] as? Boolean ?: false
            )
        }
    }*/
