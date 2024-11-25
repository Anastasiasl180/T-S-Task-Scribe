package com.aopr.tasks_data.scheduled_notifucation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
fun scheduleTaskReminder(
    context: Context,
    taskId: Int,
    taskTitle: String,
    date: LocalDate,
    time: LocalTime,
    subTaskDescription: String? = null
) {
    val timeInMillis = getTimeInMillis(date, time)

    val requestCode = if (subTaskDescription != null) {
        (taskId.toString() + subTaskDescription.hashCode().toString()).hashCode()
    } else {
        (taskId.toString() + timeInMillis.toString()).hashCode()
    }

    val intent = Intent(context, TaskReminderReceiver::class.java).apply {
        putExtra("TASK_ID", taskId)
        putExtra("TASK_TITLE", taskTitle)
        putExtra("SUBTASK_DESCRIPTION", subTaskDescription)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        timeInMillis,
        pendingIntent
    )
}
fun cancelTaskReminder(context: Context, taskId: Int, taskTitle: String, date: LocalDate, time: LocalTime) {
    val timeInMillis = getTimeInMillis(date, time)

    val requestCode = (taskId.toString() + timeInMillis.toString()).hashCode()

    val intent = Intent(context, TaskReminderReceiver::class.java).apply {
        putExtra("TASK_ID", taskId)
        putExtra("TASK_TITLE", taskTitle)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.cancel(pendingIntent)
}
fun cancelSubtaskReminder(context: Context, taskId: Int, taskTitle: String, subTaskDescription: String, date: LocalDate, time: LocalTime) {
    val timeInMillis = getTimeInMillis(date, time)

    val requestCode = (taskId.toString() + subTaskDescription.hashCode().toString()).hashCode()

    val intent = Intent(context, TaskReminderReceiver::class.java).apply {
        putExtra("TASK_ID", taskId)
        putExtra("TASK_TITLE", taskTitle)
        putExtra("SUBTASK_DESCRIPTION", subTaskDescription)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.cancel(pendingIntent)
}


fun getTimeInMillis(localDate: LocalDate, localTime: LocalTime): Long {
    val localDateTime = localDate.atTime(localTime)
    val zoneId = ZoneId.systemDefault()
    val zoneDateTime = localDateTime.atZone(zoneId)
    return zoneDateTime.toInstant().toEpochMilli()
}