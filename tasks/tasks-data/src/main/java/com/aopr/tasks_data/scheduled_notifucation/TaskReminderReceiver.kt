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
            showNotifications(context, taskId, taskTittle,subTaskDescription)
        }
    }


    private fun showNotifications(context: Context, taskId: Int, taskTittle: String,subTaskDescription:String?) {

        val channelId = "task_reminder_channel"
        val channelName = "Task Reminders"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel for task and subtask reminders"
        }
        notificationManager.createNotificationChannel(channel)
        val notificationText = subTaskDescription?.let {
            "Subtask from task \"$taskTittle\":$it"
        } ?:taskTittle
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Reminder")
            .setContentText(notificationText)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(taskId, notification)

    }
}