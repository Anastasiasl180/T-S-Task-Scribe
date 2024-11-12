package com.aopr.tasks_data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_domain.models.Subtasks
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val tittle:String,
    val description:String,
    val date:LocalDate,
    val time:LocalTime,
    val isCompleted:Boolean,
    val importance: ImportanceOfTask =ImportanceOfTask.MEDIUM,
    val listOfSubtasks:List<Subtasks>
)
