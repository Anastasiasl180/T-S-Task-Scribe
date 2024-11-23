package com.aopr.tasks_domain.models

import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val id:Int,
    val tittle:String,
    val description:String,
    val date: LocalDate?,
    val time: LocalTime?,
    val isCompleted:Boolean,
    val importance: ImportanceOfTask =ImportanceOfTask.MEDIUM,
    val listOfSubtasks:List<Subtasks>?
)

data class Subtasks(
    val description:String,
    val isCompleted:Boolean
)
enum class ImportanceOfTask{
    HIGH,MEDIUM,LOW
}