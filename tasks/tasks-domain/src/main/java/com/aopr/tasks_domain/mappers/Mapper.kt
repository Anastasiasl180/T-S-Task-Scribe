package com.aopr.tasks_domain.mappers

import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_domain.models.Subtasks
import com.aopr.tasks_domain.models.Task
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID


fun Subtasks.subtaskToFirestore(): Map<String, Any?> {
    return mapOf(
        "description" to description,
        "isCompleted" to isCompleted,
        "date" to date?.format(DateTimeFormatter.ISO_LOCAL_DATE),
        "time" to time?.format(DateTimeFormatter.ISO_LOCAL_TIME)
    )
}

fun subtaskFromFirestore(data: Map<String, Any?>): Subtasks {
    return Subtasks(
        description = data["description"] as? String ?: "",
        isCompleted = data["isCompleted"] as? Boolean ?: false,
        date = (data["date"] as? String)?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) },
        time = (data["time"] as? String)?.let { LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME) }
    )
}

fun Task.toFireStore():Map<String,Any?>{
    return mapOf(
        "id" to id,
        "uuid" to uuid.toString(),
        "tittle" to tittle,
        "description" to description,
        "dateForReminder" to dateForReminder?.format(DateTimeFormatter.ISO_LOCAL_DATE),
        "dateOfTaskToBeDone" to dateOfTaskToBeDone?.format(DateTimeFormatter.ISO_LOCAL_DATE),
        "timeForReminder" to timeForReminder?.format(DateTimeFormatter.ISO_LOCAL_TIME),
        "isCompleted" to isCompleted,
        "importance" to importance.name,
        "listOfSubtasks" to listOfSubtasks?.map { it.subtaskToFirestore() }
    )
}
fun taskFromFirestore(data: Map<String, Any?>): Task {
    return Task(
        id = (data["id"] as? Long)?.toInt() ?: 0,
        uuid = UUID.fromString(data["uuid"] as? String ?: UUID.randomUUID().toString()),
        tittle = data["tittle"] as? String ?: "",
        description = data["description"] as? String ?: "",
        dateForReminder = (data["dateForReminder"] as? String)?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) },
        dateOfTaskToBeDone = (data["dateOfTaskToBeDone"] as? String)?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) },
        timeForReminder = (data["timeForReminder"] as? String)?.let { LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME) },
        isCompleted = data["isCompleted"] as? Boolean ?: false,
        importance = ImportanceOfTask.valueOf(data["importance"] as? String ?: "MEDIUM"),
        listOfSubtasks = (data["listOfSubtasks"] as? List<Map<String, Any?>>)?.map { subtaskFromFirestore(it) }
    )
}