package com.example.yandexmds.domain.repository

import androidx.lifecycle.LiveData
import com.example.yandexmds.domain.model.ScheduleItemEntity

interface ScheduleRepository {
    suspend fun addScheduleItem(item: ScheduleItemEntity)
    suspend fun deleteScheduleItem(item: ScheduleItemEntity)
    suspend fun editScheduleItem(item: ScheduleItemEntity)
    suspend fun getScheduleItem(id: Int): ScheduleItemEntity
    fun getScheduleList(): LiveData<List<ScheduleItemEntity>>
    suspend fun deleteAllScheduleItems()
}