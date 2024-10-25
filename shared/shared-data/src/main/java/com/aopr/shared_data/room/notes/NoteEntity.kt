package com.aopr.shared_data.room.notes

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val tittle:String,
    val description:String
)
