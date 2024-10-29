package com.aopr.notes_data.mapper

import com.aopr.notes_data.room.notes.NoteEntity
import com.aopr.notes_domain.models.Note

internal fun Note.mapToEntity():NoteEntity{
    return NoteEntity(
        id = id, tittle = tittle, description = description
    )
}
internal fun NoteEntity.mapToNote():Note{
    return Note(
        id = id, tittle = tittle, description = description

    )
}