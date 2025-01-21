package com.example.yandexmds.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToDoItemDBO::class, ScheduleItemDBO::class], version = 10)
@TypeConverters(WeekdayConverter::class)
abstract class DataBaseToDo: RoomDatabase() {
    abstract fun toDoListDao(): ToDoListDao
    abstract fun scheduleDao(): ScheduleDao

    companion object{
        private var INSTANCE: DataBaseToDo? = null
        private val LOCK = Any()
        private const val DB_NAME = "todo.db"

        fun getInstance(application: Application): DataBaseToDo{
            INSTANCE?.let{
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let{
                    it
                }
                val db = Room.databaseBuilder(application,
                    DataBaseToDo::class.java,
                    DB_NAME)
                    .addMigrations(MIGRATION_6_7)
                    .addMigrations(MIGRATION_7_8)
                    .addMigrations(MIGRATION_8_9)
                    .addMigrations(MIGRATION_9_10)
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}