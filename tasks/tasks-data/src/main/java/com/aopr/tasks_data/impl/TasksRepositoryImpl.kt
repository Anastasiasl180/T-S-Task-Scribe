package com.aopr.tasks_data.impl

import android.content.Context
import com.aopr.tasks_data.mapper.mapToEntity
import com.aopr.tasks_data.mapper.mapToTask
import com.aopr.tasks_data.room.TasksDao
import com.aopr.tasks_data.scheduled_notifucation.cancelSubtaskReminder
import com.aopr.tasks_data.scheduled_notifucation.cancelTaskReminder
import com.aopr.tasks_data.utils.FieldsValidator
import com.aopr.tasks_domain.interactors.TasksRepository
import com.aopr.tasks_domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import java.time.LocalDate

@Single
class TasksRepositoryImpl(private val dao: TasksDao, private val context: Context) :
    TasksRepository {
    override suspend fun createTask(task: Task) {
        val existingTask = dao.getTaskById(task.id).firstOrNull()
        if (existingTask != null) {
            updateTask(existingTask.mapToTask())
        } else {
            FieldsValidator.validateTask(
                task.tittle,
                task.description,
                task.dateForReminder,
                task.timeForReminder,
                task.dateOfTaskToBeDone
            )
            val list = if (task.listOfSubtasks != null) {
                task.listOfSubtasks!!.map { sub ->
                    FieldsValidator.validateSubTask(sub.description, sub.date, sub.time)
                    sub
                }
            } else {
                null
            }
            FieldsValidator.scheduleReminders(task, list, context)
            dao.insertTask(task.mapToEntity().copy(listOfSubtasks = list))
        }

    }
    //hi

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.mapToEntity())
        if (task.dateForReminder != null && task.timeForReminder != null) {
            cancelTaskReminder(
                context,
                taskId = task.uuid,
                taskTitle = task.tittle,
                date = task.dateForReminder!!,
                time = task.timeForReminder!!
            )
        }
        val listOfSubsWithRemi = task.listOfSubtasks?.filter {
            it.time != null
            it.date != null
        }
        listOfSubsWithRemi?.forEach { subtask ->
            cancelSubtaskReminder(
                context,
                subTaskId = task.uuid,
                taskTitle = task.tittle,
                subTaskDescription = subtask.description,
                date = subtask.date!!,
                time = subtask.time!!
            )

        }
    }

    override suspend fun updateTask(task: Task) {

        FieldsValidator.validateTask(
            task.tittle,
            task.description,
            task.dateForReminder,
            task.timeForReminder,
            task.dateOfTaskToBeDone
        )
        val list = if (task.listOfSubtasks != null) {
            task.listOfSubtasks!!.map { sub ->
                FieldsValidator.validateSubTask(sub.description, sub.date, sub.time)
                sub
            }
        } else {
            null
        }
        FieldsValidator.scheduleReminders(task, list, context)

        dao.updateTask(task.mapToEntity().copy(listOfSubtasks = list))
    }

    override suspend fun getTaskBuId(id: Int): Flow<Task> {
        return dao.getTaskById(id).map { it.mapToTask() }
    }

    override suspend fun getAllTasks(): Flow<List<Task>> {
        return dao.getALlTasks().map { list ->
            list.map { entity -> entity.mapToTask() }
        }
    }

    override suspend fun deleteSubTask(task: Task?, indexOfSubTak: Int) {
        if (task != null) {
            val existingTask = dao.getTaskById(task.id).first()
            val list = existingTask.listOfSubtasks
            if (list != null) {
                val subTaskRemi = list[indexOfSubTak]
                if (subTaskRemi.time != null && subTaskRemi.date != null) {
                    cancelSubtaskReminder(
                        context,
                        subTaskId = existingTask.uuid,
                        taskTitle = existingTask.tittle,
                        subTaskDescription = subTaskRemi.description,
                        date = subTaskRemi.date!!,
                        time = subTaskRemi.time!!
                    )
                }
                val updatedList = list.toMutableList().apply {
                    removeAt(indexOfSubTak)
                }
                val updatedTask = existingTask.mapToTask().copy(listOfSubtasks = updatedList)
                updateTask(updatedTask)
            }
        }
    }

    override suspend fun getTasksByDate(date: LocalDate): Flow<List<Task>> {
        return dao.getTasksByDate(date).map { list ->
            list.map { task -> task.mapToTask() }

        }
    }

}