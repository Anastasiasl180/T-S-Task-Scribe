package com.aopr.notes_data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val tittle:String,
    val description:String,
    val timestamp:Long,
    val isPinned:Boolean =false
)
