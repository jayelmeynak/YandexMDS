package com.example.yandexmds.domain.useCases.schedule

import androidx.lifecycle.LiveData
import com.example.yandexmds.domain.model.ScheduleItemEntity

class GetScheduleListUseCase(private val repository: ScheduleRepository) {
    fun getScheduleList(): LiveData<List<ScheduleItemEntity>> {
        return repository.getScheduleList()
    }
}