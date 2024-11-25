package com.aopr.tasks_data.scheduled_notifucation

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.aopr.tasks_data.R

class TaskReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra("TASK_ID", -1)
        val taskTittle = intent.getStringExtra("TASK_TITLE") ?: "Task reminder"
        val subTaskDescription = intent.getStringExtra("SUBTASK_DESCRIPTION")
        if (taskId != -1) {
            if (subTaskDescription != null) {
                showSubtaskNotification(context, taskId, taskTittle, subTaskDescription)
            } else {
                showTaskNotification(context, taskId, taskTittle)
            }
        }
    }

    private fun showTaskNotification(context: Context, taskId: Int, taskTitle: String) {
        val channelId = "task_reminder_channel"
        val channelName = "Task Reminders"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Task Reminder")
            .setContentText(taskTitle)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(taskId, notification)
    }

    private fun showSubtaskNotification(
        context: Context,
        taskId: Int,
        taskTitle: String,
        subTaskDescription: String
    ) {
        val channelId = "subtask_reminder_channel"
        val channelName = "Subtask Reminders"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationText = "Subtask: $subTaskDescription (Task: $taskTitle)"

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Subtask Reminder")
            .setContentText(notificationText)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(taskId + subTaskDescription.hashCode(), notification)
    }
}