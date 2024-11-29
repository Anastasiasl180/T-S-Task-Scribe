package com.aopr.tasks_data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_domain.models.Subtasks
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val uuid:UUID,
    val tittle:String,
    val description:String,
    val dateToBeDone:LocalDate?,
    val dateForReminder:LocalDate? = null,
    val time:LocalTime? = null,
    val isCompleted:Boolean,
    val importance: ImportanceOfTask =ImportanceOfTask.MEDIUM,
    val listOfSubtasks:List<Subtasks>?
)






class Converts {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? = value?.toString()

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it) }

    @TypeConverter
    fun fromListOfSubtasks(value: List<Subtasks>?): String? {
        return value?.joinToString(separator = ";") {
            "${it.description}|${it.isCompleted}|${it.date}|${it.time}"
        }
    }

    @TypeConverter
    fun toListOfSubtasks(value: String?): List<Subtasks>? {
        return value?.split(";")?.mapNotNull { subtaskString ->
            val parts = subtaskString.split("|")
            if (parts.size == 4) {
                Subtasks(
                    description = parts[0],
                    isCompleted = parts[1].toBoolean(),
                    date = parts[2].takeIf { it != "null" }?.let { LocalDate.parse(it) },
                    time = parts[3].takeIf { it != "null" }?.let { LocalTime.parse(it) }
                )
            } else {
                null
            }
        }
    }}