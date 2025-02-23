package com.aopr.tasks_domain.interactors

import com.aopr.shared_domain.Responses
import com.aopr.shared_domain.resource_manager.SharedStringResourceManager
import com.aopr.shared_domain.throws.EmptyDateForReminderException
import com.aopr.shared_domain.throws.EmptyDayToBeDoneException
import com.aopr.shared_domain.throws.EmptyDescriptionException
import com.aopr.shared_domain.throws.EmptyTimeForReminderException
import com.aopr.shared_domain.throws.EmptyTittleException
import com.aopr.shared_domain.throws.SetCorrectTimeForReminderException
import com.aopr.tasks_domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import java.io.IOException
import java.time.LocalDate

@Single
class TasksUseCase(private val repository: TasksRepository) {

    fun deleteAllTasks(): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.deleteAllTask()
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: EmptyDescriptionException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyDescriptionMessage.messageId))

        }
    }

    fun setTasksFromFire(tasks: List<Task>?): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.setTasksFromFire(tasks)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: EmptyDescriptionException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyDescriptionMessage.messageId))

        }
    }

    fun createTask(task: Task): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.createTask(task)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: EmptyDescriptionException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyDescriptionMessage.messageId))

        } catch (e: EmptyTittleException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyTittleMessage.messageId))

        } catch (e: EmptyDateForReminderException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyDataForReminderMessage.messageId))

        } catch (e: EmptyTimeForReminderException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyTimeForReminderMessage.messageId))

        } catch (e: EmptyDayToBeDoneException) {
            emit(Responses.Error(SharedStringResourceManager.EmptyDataForTaskToBeDoneMessage.messageId))

        } catch (e: SetCorrectTimeForReminderException) {
            emit(Responses.Error(SharedStringResourceManager.WrongTimeForReminderMessage.messageId))

        }

    }

    fun deleteTask(task: List<Task>): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.deleteChosenTasks(task)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: Exception) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        }
    }

    fun getTaskById(id: Int): Flow<Responses<Flow<Task>>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.getTaskById(id)
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

    fun deleteSubTask(task: Task, indexOfSubTask: Int): Flow<Responses<Unit>> = flow {
        try {
            emit(Responses.Loading())
            val data = repository.deleteSubTask(task, indexOfSubTask)
            emit(Responses.Success(data))
        } catch (e: IOException) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))
        } catch (e: Exception) {
            emit(Responses.Error(SharedStringResourceManager.DefaultMessage.messageId))

        }
    }

}
