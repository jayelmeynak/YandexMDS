package com.example.yandexmds.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM ScheduleItemDBO")
    fun getAllScheduleItems(): LiveData<List<ScheduleItemDBO>>

    @Query("SELECT * FROM ScheduleItemDBO WHERE id = :id")
    suspend fun getScheduleItem(id: Int): ScheduleItemDBO

    @Insert
    suspend fun insertScheduleItem(scheduleItem: ScheduleItemDBO)

    @Update
    suspend fun updateScheduleItem(scheduleItem: ScheduleItemDBO)

    @Delete
    suspend fun deleteScheduleItem(scheduleItem: ScheduleItemDBO)
}
