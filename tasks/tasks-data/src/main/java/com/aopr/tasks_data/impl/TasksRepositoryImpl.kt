package com.aopr.tasks_data.impl

import android.content.Context
import android.util.Log
import com.aopr.shared_domain.throws.EmptyDateForReminderException
import com.aopr.shared_domain.throws.EmptyDescriptionException
import com.aopr.shared_domain.throws.EmptyTimeForReminderException
import com.aopr.shared_domain.throws.EmptyTittleException
import com.aopr.tasks_data.mapper.mapToEntity
import com.aopr.tasks_data.mapper.mapToTask
import com.aopr.tasks_data.room.TasksDao
import com.aopr.tasks_data.scheduled_notifucation.scheduleTaskReminder
import com.aopr.tasks_domain.interactors.TasksRepository
import com.aopr.tasks_domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton
import java.time.LocalDate

@Single
class TasksRepositoryImpl(private val dao: TasksDao, private val context: Context) :
    TasksRepository {
    override suspend fun createTask(task: Task) {
        if (task.tittle.isBlank()) throw EmptyTittleException()
        if (task.description.isBlank()) throw EmptyDescriptionException()

        var res = false
        if (task.date != null) {
            if (task.time != null) {
                res = true
            }
        }
        if (task.date == null) {
            throw EmptyDateForReminderException()
        }
        if (task.time == null) {
            throw EmptyTimeForReminderException()
        }


        val existingTask = dao.getTaskById(task.id).firstOrNull()
        if (existingTask!=null) {
            Log.wtf("Meerka", "dfdf23: ")
           dao.updateTask(task.mapToEntity())
        } else {
            dao.insertTask(task.mapToEntity())
            if (res == true) {
                scheduleTaskReminder(
                    context = context,
                    task.id,
                    task.tittle,
                    date = task.date!!,
                    time = task.time!!
                )
            }
        }


    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.mapToEntity())
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task.mapToEntity())
    }

    override suspend fun getTaskBuId(id: Int): Flow<Task> {
        return dao.getTaskById(id).map { it.mapToTask() }
    }

    override suspend fun getAllTasks(): Flow<List<Task>> {
        return dao.getALlTasks().map { list ->
            list.map { entity -> entity.mapToTask() }
        }
    }

    override suspend fun getTasksByDate(date: LocalDate): Flow<List<Task>> {
        return dao.getTasksByDate(date).map { list ->
            list.map { task -> task.mapToTask() }

        }
    }

}