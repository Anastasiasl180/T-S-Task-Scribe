package com.aopr.tasks_data.utils

import android.content.Context
import com.aopr.shared_domain.throws.EmptyDateForReminderException
import com.aopr.shared_domain.throws.EmptyDayToBeDoneException
import com.aopr.shared_domain.throws.EmptyDescriptionException
import com.aopr.shared_domain.throws.EmptyTimeForReminderException
import com.aopr.shared_domain.throws.EmptyTittleException
import com.aopr.shared_domain.throws.SetCorrectTimeForReminderException
import com.aopr.tasks_data.scheduled_notifucation.scheduleTaskReminder
import com.aopr.tasks_domain.models.Subtasks
import com.aopr.tasks_domain.models.Task
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class FieldsValidator {

    companion object {

        fun validateTask(
            tittle: String,
            description: String,
            date: LocalDate? = null,
            time: LocalTime? = null,
            dateForToBeDone: LocalDate? = null,
        ) {
            validateDateTimeNotInPast(date, time)
            if (date != null && time == null) throw EmptyTimeForReminderException()
            if (time != null && date == null) throw EmptyDateForReminderException()
            if (tittle.isBlank()) throw EmptyTittleException()
            if (description.isBlank()) throw EmptyDescriptionException()
            if (dateForToBeDone == null) throw EmptyDayToBeDoneException()
        }

        fun validateSubTask(
            tittle: String,
            date: LocalDate? = null,
            time: LocalTime? = null,
        ) {
            validateDateTimeNotInPast(date, time)
            if (date != null && time == null) throw EmptyTimeForReminderException()
            if (time != null && date == null) throw EmptyDateForReminderException()
            if (tittle.isBlank()) throw EmptyTittleException()
        }

        private fun validateDateTimeNotInPast(date: LocalDate?, time: LocalTime?) {
            if (date != null && time != null) {
                val now = LocalDateTime.now()
                val selected = LocalDateTime.of(date, time)

                if (selected.isBefore(now)) {
                    throw SetCorrectTimeForReminderException()
                }
            }
        }

        fun scheduleReminders(task: Task, subtasks: List<Subtasks>?, context: Context) {
            if (task.dateForReminder != null && task.timeForReminder != null) {
                scheduleTaskReminder(
                    context = context,
                    taskId = task.uuid,
                    taskTitle = task.tittle,
                    date = task.dateForReminder!!,
                    time = task.timeForReminder!!
                )
            }

            subtasks?.forEach { subtask ->
                if (subtask.date != null && subtask.time != null) {
                    scheduleTaskReminder(
                        context = context,
                        taskId = task.uuid,
                        taskTitle = "${task.tittle} - Subtask",
                        date = subtask.date!!,
                        time = subtask.time!!,
                        subTaskDescription = subtask.description
                    )
                }
            }
        }
    }
}


