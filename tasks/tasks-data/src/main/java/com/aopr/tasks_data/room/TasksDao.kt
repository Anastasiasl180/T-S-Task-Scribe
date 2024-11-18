package com.aopr.tasks_data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM TaskEntity WHERE id = :id LIMIT 1")
    fun getTaskById(id: Int): Flow<TaskEntity>

    @Query("SELECT * FROM TaskEntity")
    fun getALlTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE date = :date")
    fun getTasksForDate(date: LocalDate): Flow<List<TaskEntity>>


}