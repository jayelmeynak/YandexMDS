package com.example.yandexmds.di

import android.content.Context
import androidx.room.Room
import com.example.yandexmds.data.DataBaseToDo
import com.example.yandexmds.data.DataBaseToDo.Companion.DB_NAME
import com.example.yandexmds.data.MIGRATION_6_7
import com.example.yandexmds.data.MIGRATION_7_8
import com.example.yandexmds.data.MIGRATION_8_9
import com.example.yandexmds.data.MIGRATION_9_10
import com.example.yandexmds.data.ScheduleDao
import com.example.yandexmds.data.ScheduleRepositoryImpl
import com.example.yandexmds.data.ToDoListDao
import com.example.yandexmds.data.ToDoListRepositoryImpl
import com.example.yandexmds.domain.repository.ScheduleRepository
import com.example.yandexmds.domain.repository.ToDoListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext application: Context
    ) = Room.databaseBuilder(
        application,
        DataBaseToDo::class.java,
        DB_NAME
    )
        .addMigrations(MIGRATION_6_7)
        .addMigrations(MIGRATION_7_8)
        .addMigrations(MIGRATION_8_9)
        .addMigrations(MIGRATION_9_10)
        .build()

    @Provides
    @Singleton
    fun provideToDoListDao(db: DataBaseToDo) = db.toDoListDao()

    @Provides
    @Singleton
    fun provideScheduleDao(db: DataBaseToDo) = db.scheduleDao()

    @Provides
    @Singleton
    fun provideScheduleRepository(scheduleDao: ScheduleDao): ScheduleRepository = ScheduleRepositoryImpl(scheduleDao)

    @Provides
    @Singleton
    fun provideToDoListRepository(toDoListDao: ToDoListDao): ToDoListRepository = ToDoListRepositoryImpl(toDoListDao)
}