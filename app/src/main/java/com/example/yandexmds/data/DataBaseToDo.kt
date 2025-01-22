package com.example.yandexmds.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToDoItemDBO::class, ScheduleItemDBO::class], version = 10)
@TypeConverters(WeekdayConverter::class)
abstract class DataBaseToDo: RoomDatabase() {
    abstract fun toDoListDao(): ToDoListDao
    abstract fun scheduleDao(): ScheduleDao

    companion object{
        const val DB_NAME = "todo.db"
    }
}