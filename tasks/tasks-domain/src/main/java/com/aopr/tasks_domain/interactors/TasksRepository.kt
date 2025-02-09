package com.aopr.tasks_domain.interactors

import com.aopr.tasks_domain.models.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate


interface TasksRepository {
    suspend fun deleteAllTask()
    suspend fun createTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun getAllTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: Int): Flow<Task>
    suspend fun setTasksFromFire(tasks: List<Task>?)
    suspend fun deleteChosenTasks(task: List<Task>)
    suspend fun deleteSubTask(task: Task, indexOfSubTak: Int)
    suspend fun getTasksByDate(date: LocalDate): Flow<List<Task>>
}