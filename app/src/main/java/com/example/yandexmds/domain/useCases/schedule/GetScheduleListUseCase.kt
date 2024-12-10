package com.example.yandexmds.domain.useCases.schedule

import androidx.lifecycle.LiveData
import com.example.yandexmds.domain.model.ScheduleItemEntity
import com.example.yandexmds.domain.repository.ScheduleRepository

class GetScheduleListUseCase(private val repository: ScheduleRepository) {
    fun getScheduleList(): LiveData<List<ScheduleItemEntity>> {
        return repository.getScheduleList()
    }
}