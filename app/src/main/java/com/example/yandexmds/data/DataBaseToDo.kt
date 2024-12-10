package com.example.yandexmds.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoItemDBO::class, ScheduleItemDBO::class], version = 6)
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
                    .addMigrations(MIGRATION_5_6)
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}