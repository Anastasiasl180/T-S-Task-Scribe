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
)

data class Subtasks(
    val description:String,
    val isCompleted:Boolean,
    val date:LocalDate? = null,
    val time:LocalTime?=null,
    val isSubTaskSaved:Boolean = false
)
enum class ImportanceOfTask{
    HIGH,MEDIUM,LOW
}
