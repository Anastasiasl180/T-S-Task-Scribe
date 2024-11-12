package com.aopr.tasks_domain.interactors

import com.aopr.tasks_domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun createTask(task:Task)
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun getTaskBuId(id:Int):Flow<Task>
    suspend fun getAllTasks():Flow<List<Task>>
}