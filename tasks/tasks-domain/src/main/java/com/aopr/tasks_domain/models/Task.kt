package com.aopr.tasks_domain.models

import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val id:Int,
    val tittle:String,
    val description:String,
    val dateForReminder: LocalDate?=null,
    val dateOfTaskToBeDone: LocalDate,
    val timeForReminder: LocalTime? = null,
    val isCompleted:Boolean,
    val importance: ImportanceOfTask =ImportanceOfTask.MEDIUM,
    val listOfSubtasks:List<Subtasks>?
)

data class Subtasks(
    val description:String,
    val isCompleted:Boolean,
    val date:LocalDate? = null,
    val time:LocalTime?=null
)
enum class ImportanceOfTask{
    HIGH,MEDIUM,LOW
}