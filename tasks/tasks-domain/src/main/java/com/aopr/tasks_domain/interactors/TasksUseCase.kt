package com.aopr.tasks_domain.interactors

import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.resource_manager.SharedStringResourceManager
import com.aopr.shared_domain.throws.EmptyDateForReminderException
import com.aopr.shared_domain.throws.EmptyDescriptionException
import com.aopr.shared_domain.throws.EmptyTimeForReminderException
import com.aopr.shared_domain.throws.EmptyTittleException
import com.aopr.tasks_domain.R
import com.aopr.tasks_domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException
import java.time.LocalDate

@Single
class TasksUseCase(private val repository: TasksRepository) {

    fun createTask(task: Task): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.createTask(task)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: EmptyDescriptionException) {
            emit(Responses.Error(com.aopr.shared_domain.R.string.EmptyDescription))
        } catch (e: EmptyTittleException) {
            emit(Responses.Error(com.aopr.shared_domain.R.string.EmptyTittle))
        } catch (e: EmptyDateForReminderException) {
            emit(Responses.Error(com.aopr.shared_domain.R.string.EmptyDateReminder))
        } catch (e: EmptyTimeForReminderException) {
            emit(Responses.Error(com.aopr.shared_domain.R.string.EmptyTimeReminder))
        }

    }

    fun deleteTask(task: Task): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.deleteTask(task)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: EmptyTittleException) {
            emit(Responses.Error(com.aopr.shared_domain.R.string.EmptyTittle))
        } catch (e: EmptyDescriptionException) {
            emit(Responses.Error(com.aopr.shared_domain.R.string.EmptyDescription))
        }
    }

    fun getTaskById(id: Int): Flow<Responses<Flow<Task>>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.getTaskBuId(id)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: Exception) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        }
    }

    fun getAllTasks(): Flow<Responses<Flow<List<Task>>>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.getAllTasks()
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: Exception) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        }
    }

    fun getTasksByDate(date: LocalDate): Flow<Responses<Flow<List<Task>>>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.getTasksByDate(date)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))

        } catch (e: Exception) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))

        }
    }

}
