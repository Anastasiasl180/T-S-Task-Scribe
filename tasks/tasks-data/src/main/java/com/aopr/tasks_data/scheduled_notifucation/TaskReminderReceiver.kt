package com.aopr.tasks_data.scheduled_notifucation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import com.aopr.tasks_data.R
import com.aopr.tasks_data.mapper.mapToTask
import com.aopr.tasks_data.room.TasksDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate
import java.util.UUID

class TaskReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras!!.getParcelable("TASK_ID", UUID::class.java)
        } else {
            intent.extras!!.getParcelable("TASK_ID")

        }
        val taskTitle = intent.getStringExtra("TASK_TITLE") ?: "Task Reminder"
        val subTaskDescription = intent.getStringExtra("SUBTASK_DESCRIPTION")

        if (taskId != null) {
            if (subTaskDescription != null) {
                showSubtaskNotification(context, taskId, taskTitle, subTaskDescription)
            } else {
                showTaskNotification(context, taskId, taskTitle)
            }
        }
    }

    private fun showTaskNotification(context: Context, taskId: UUID, taskTitle: String) {
        val channelId = "task_reminder_channel"
        val channelName = "Task Reminders"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for task reminders"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Task Reminder")
            .setContentText(taskTitle)
            .setSmallIcon(R.drawable.icon_for_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(taskId.hashCode(), notification)
    }

    private fun showSubtaskNotification(
        context: Context,
        taskId: UUID,
        taskTitle: String,
        subTaskDescription: String
    ) {
        val channelId = "subtask_reminder_channel"
        val channelName = "Subtask Reminders"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for subtask reminders"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Subtask Reminder")
            .setContentText("Subtask: $subTaskDescription (Task: $taskTitle)")
            .setSmallIcon(R.drawable.icon_for_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(taskId.hashCode() + subTaskDescription.hashCode(), notification)
    }


}

class DailyCheckReceiver() : BroadcastReceiver(), KoinComponent {

    private val dao by inject<TasksDao>()

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { safeContext ->

            CoroutineScope(Dispatchers.IO).launch {
                val today = LocalDate.now()

                val tasksForToday = dao
                    .getTasksByDate(today)
                    .map { entities -> entities.map { it.mapToTask() } }
                    .first()


                val uncompleted = tasksForToday.filter { !it.isCompleted }

                if (uncompleted.isNotEmpty()) {

                    showUncompletedTasksNotification(safeContext, uncompleted.size)
                }
            }
        }
    }

    private fun showUncompletedTasksNotification(context: Context, count: Int) {
        val channelId = "daily_check_channel"
        val channelName = "Daily Check Reminders"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = R.string.dailyCheckTime.toString()
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(R.string.unCompletedTasksForToday.toString())
            .setContentText("You have $count uncompleted task(s) due today.")
            .setSmallIcon(R.drawable.icon_for_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(999, notification)
    }
}