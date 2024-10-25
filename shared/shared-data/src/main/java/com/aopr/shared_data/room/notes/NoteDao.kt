package com.aopr.shared_data.room.notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    fun getNoteById(id: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity")
    fun getALlNotes(): Flow<List<NoteEntity>>
}