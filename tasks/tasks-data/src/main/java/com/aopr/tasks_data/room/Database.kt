package com.aopr.tasks_data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.koin.core.annotation.Single


@Single
fun provideAppDataBase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context = context,
        AppDatabase::class.java,
        "app-database"
    ).build()
}


@Single
fun provideTaskDao(appDataBase: AppDatabase):TasksDao {
    return appDataBase.taskDao()
}

@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(Converts::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun taskDao():TasksDao
}