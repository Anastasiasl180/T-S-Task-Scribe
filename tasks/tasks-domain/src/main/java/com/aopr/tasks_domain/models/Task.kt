package com.aopr.tasks_domain.models

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

data class Task(
    val id:Int,
    val uuid: UUID = UUID.randomUUID(),
    val tittle:String,
    val description:String,
    val dateForReminder: LocalDate?=null,
    val dateOfTaskToBeDone: LocalDate?,
    val timeForReminder: LocalTime? = null,
    val isCompleted:Boolean = false,
    val importance: ImportanceOfTask =ImportanceOfTask.MEDIUM,
    val listOfSubtasks:List<Subtasks>?
) {  fun toFirestore(): Map<String, Any?> {
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
        "listOfSubtasks" to listOfSubtasks?.map { it.toFirestore() }
    )
}

    companion object {
        fun fromFirestore(data: Map<String, Any?>): Task {
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
                listOfSubtasks = (data["listOfSubtasks"] as? List<Map<String, Any?>>)?.map { Subtasks.fromFirestore(it) }
            )
        }
    }}
//hello
data class Subtasks(
    val description:String,
    val isCompleted:Boolean,
    val date:LocalDate? = null,
    val time:LocalTime?=null
){
    fun toFirestore(): Map<String, Any?> {
        return mapOf(
            "description" to description,
            "isCompleted" to isCompleted,
            "date" to date?.format(DateTimeFormatter.ISO_LOCAL_DATE),
            "time" to time?.format(DateTimeFormatter.ISO_LOCAL_TIME)
        )
    }

    companion object {
        fun fromFirestore(data: Map<String, Any?>): Subtasks {
            return Subtasks(
                description = data["description"] as? String ?: "",
                isCompleted = data["isCompleted"] as? Boolean ?: false,
                date = (data["date"] as? String)?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) },
                time = (data["time"] as? String)?.let { LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME) }
            )
        }
    }
}
enum class ImportanceOfTask{
    HIGH,MEDIUM,LOW
}