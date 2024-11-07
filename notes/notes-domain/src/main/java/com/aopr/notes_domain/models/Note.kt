package com.aopr.notes_domain.models

data class Note(
    val id:Int,
    val tittle:String,
    val description:String,
    val date:String ="",
    val isPinned:Boolean = false
)
