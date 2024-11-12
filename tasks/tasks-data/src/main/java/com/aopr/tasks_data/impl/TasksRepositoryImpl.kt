package com.aopr.tasks_data.impl

import com.aopr.tasks_data.mapper.mapToEntity
import com.aopr.tasks_data.room.TasksDao
import com.aopr.tasks_domain.interactors.TasksRepository
import com.aopr.tasks_domain.models.Task
import kotlinx.coroutines.flow.Flow

class TasksRepositoryImpl(private val dao: TasksDao):TasksRepository {
    override suspend fun createTask(task: Task) {

        dao.insertTask(task.mapToEntity())
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.mapToEntity())
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task.mapToEntity())
    }

    override suspend fun getTaskBuId(id: Int): Flow<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTasks(): Flow<List<Task>> {
        TODO("Not yet implemented")
    }
}