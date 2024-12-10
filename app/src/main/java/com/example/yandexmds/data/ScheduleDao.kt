package com.example.yandexmds.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM ScheduleItemDBO")
    fun getAllScheduleItems(): LiveData<List<ScheduleItemDBO>>

    @Insert
    suspend fun insertScheduleItem(scheduleItem: ScheduleItemDBO)

    @Delete
    suspend fun deleteScheduleItem(scheduleItem: ScheduleItemDBO)
}
