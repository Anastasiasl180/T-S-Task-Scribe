package com.aopr.notes_data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveListOfNotes(bookmarks: List<NoteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Delete
    suspend fun deleteAllNote(note: List<NoteEntity>)

    @Query("SELECT * FROM NoteEntity WHERE id = :id LIMIT 1")
    fun getNoteById(id: Int): Flow<NoteEntity>

    @Query("SELECT * FROM NoteEntity  ORDER BY isPinned DESC, timestamp DESC")
    fun getALlNotes(): Flow<List<NoteEntity>>
}