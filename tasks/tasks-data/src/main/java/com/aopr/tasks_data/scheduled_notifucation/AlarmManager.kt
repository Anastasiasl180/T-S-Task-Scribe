package com.aopr.tasks_data.scheduled_notifucation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.UUID

fun scheduleTaskReminder(
    context: Context,
    taskId: UUID,
    taskTitle: String,
    date: LocalDate,
    time: LocalTime,
    subTaskDescription: String? = null
) {
    val timeInMillis = getTimeInMillis(date, time)

    val requestCode = (taskId.hashCode() + timeInMillis.hashCode())/3

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
fun cancelTaskReminder(context: Context, taskId: UUID, taskTitle: String, date: LocalDate, time: LocalTime) {
    val timeInMillis = getTimeInMillis(date, time)

    val requestCode = (taskId.hashCode() + timeInMillis.hashCode())/3

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

fun cancelSubtaskReminder(context: Context, subTaskId: UUID, taskTitle: String, subTaskDescription: String, date: LocalDate, time: LocalTime) {
    val timeInMillis = getTimeInMillis(date, time)

    val requestCode = (subTaskId.hashCode() + timeInMillis.hashCode())/3

    val intent = Intent(context, TaskReminderReceiver::class.java).apply {
        putExtra("TASK_ID", subTaskId)
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
fun scheduleDailyCheck(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, DailyCheckReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        12345,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 21)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        if (before(Calendar.getInstance())) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}