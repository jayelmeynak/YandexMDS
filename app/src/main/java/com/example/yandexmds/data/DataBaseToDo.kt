package com.example.yandexmds.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoItemDBO::class], version = 3)
abstract class DataBaseToDo: RoomDatabase() {
    abstract fun toDoListDao(): ToDoListDao

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
                    .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}