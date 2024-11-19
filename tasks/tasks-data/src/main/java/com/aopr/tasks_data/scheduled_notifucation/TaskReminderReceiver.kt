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
        Log.wtf("Meerka", "gfgfg32: ")
        val taskId = intent.getIntExtra("TASK_ID", -1)
        val taskTittle = intent.getStringExtra("TASK_TITLE") ?: "Task reminder"

        if (taskId != -1) {
            Log.wtf("Meerka", "ioio: ")
            showNotifications(context, taskId, taskTittle)
        }
    }


    private fun showNotifications(context: Context, taskId: Int, taskTittle: String) {
        try {
            
        Log.wtf("Meerka", "gfgfg323: ")
        val channelId = "task_reminder_channel"
        val channelName = "Task Reminders"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel for task reminders"
        }
        notificationManager.createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Reminder")
            .setContentText(taskTittle)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(taskId, notification)

    }catch (e:Exception){
        Log.wtf("Meerka", e.message.toString())
            Log.wtf("Meerka", e.localizedMessage.toString())
    } }
}