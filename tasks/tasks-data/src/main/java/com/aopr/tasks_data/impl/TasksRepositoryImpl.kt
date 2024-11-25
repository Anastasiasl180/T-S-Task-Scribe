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
import com.aopr.tasks_domain.models.Subtasks
import com.aopr.tasks_domain.models.Task
import com.aopr.tasks_domain.throws.EmptyDateForToDo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import java.time.LocalDate
import java.time.LocalTime

@Single
class TasksRepositoryImpl(private val dao: TasksDao, private val context: Context) :
    TasksRepository {
    override suspend fun createTask(task: Task) {
        if (task.tittle.isBlank()) throw EmptyTittleException()
        if (task.description.isBlank()) throw EmptyDescriptionException()
        if (task.dateOfTaskToBeDone == null) throw EmptyDateForToDo()

        validateDateTime(task.dateForReminder, task.timeForReminder, "Task reminder")

        val updatedListOfSubtasks = task.listOfSubtasks?.map { subtask ->
            validateDateTime(subtask.date, subtask.time, "Subtask reminder")
            subtask
        }

        val existingTask = dao.getTaskById(task.id).firstOrNull()

        if (existingTask != null) {
            val updatedTaskEntity = task.mapToEntity().copy(listOfSubtasks = updatedListOfSubtasks)
            dao.updateTask(updatedTaskEntity)
        } else {
             dao.insertTask(task.mapToEntity().copy(listOfSubtasks = updatedListOfSubtasks))
        }

        scheduleReminders(task, updatedListOfSubtasks)
    }

    private fun validateDateTime(date: LocalDate?, time: LocalTime?, context: String) {
        if (date != null && time == null) throw EmptyTimeForReminderException()
        if (time != null && date == null) throw EmptyDateForReminderException()
    }

    private fun scheduleReminders(task: Task, subtasks: List<Subtasks>?) {
        if (task.dateForReminder != null && task.timeForReminder != null) {
            scheduleTaskReminder(
                context = context,
                taskId = task.id,
                taskTitle = task.tittle,
                date = task.dateForReminder!!,
                time = task.timeForReminder!!
            )
        }

        subtasks?.forEach { subtask ->
            if (subtask.date != null && subtask.time != null) {
                scheduleTaskReminder(
                    context = context,
                    taskId = task.id,
                    taskTitle = "${task.tittle} - Subtask",
                    date = subtask.date!!,
                    time = subtask.time!!,
                    subTaskDescription = subtask.description // Use description for unique ID
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