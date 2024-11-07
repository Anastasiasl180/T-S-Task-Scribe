package com.aopr.notes_data.mapper

import com.aopr.notes_data.room.notes.NoteEntity
import com.aopr.notes_domain.models.Note
import java.text.SimpleDateFormat
import java.util.Locale

private fun formatTimestampToDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(timestamp)
}

internal fun Note.mapToEntity():NoteEntity{
    return NoteEntity(
        id = id, tittle = tittle, description = description, timestamp = System.currentTimeMillis(),
        isPinned = isPinned
    )
}
internal fun NoteEntity.mapToNote():Note{
    return Note(
        id = id, tittle = tittle, description = description, date = formatTimestampToDate(timestamp),
        isPinned = isPinned

    )
}