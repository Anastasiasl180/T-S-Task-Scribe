package com.aopr.tasks_data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.koin.core.annotation.Single


@Single
fun provideTaskDataBase(context: Context): TaskDatabase {
    return Room.databaseBuilder(
        context = context,
        TaskDatabase::class.java,
        "task-database"
    ).build()
}


@Single
fun provideTaskDao(taskDataBase: TaskDatabase):TasksDao {
    return taskDataBase.taskDao()
}

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converts::class)
abstract class TaskDatabase: RoomDatabase(){
    abstract fun taskDao():TasksDao
}