package com.aopr.tasks_data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
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
    val date:LocalDate?,
    val time:LocalTime?,
    val isCompleted:Boolean,
    val importance: ImportanceOfTask =ImportanceOfTask.MEDIUM,
    val listOfSubtasks:List<Subtasks>?
)

class Converts {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.toString()
    }
    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }
    @TypeConverter
    fun fromLocalTime(value:LocalTime?):String?{
        return value?.toString()
    }
    @TypeConverter
    fun toLocalTime(value:String?):LocalTime?{
        return value?.let {
            LocalTime.parse(it)
        }
    }
    @TypeConverter
    fun fromListOfSubtasks(value: List<Subtasks>?): String? {
        return value?.joinToString(separator = ",") { "${it.description}|${it.isCompleted}" }
    }

    @TypeConverter
    fun toListOfSubtasks(value: String?): List<Subtasks>? {
        return value?.split(",")?.map {
            val parts = it.split("|")
            Subtasks(parts[0], parts[1].toBoolean())
        }
    }



}
