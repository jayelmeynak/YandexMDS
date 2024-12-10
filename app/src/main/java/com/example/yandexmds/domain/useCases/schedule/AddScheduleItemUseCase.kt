package com.example.yandexmds.domain.useCases.schedule

import com.example.yandexmds.domain.model.ScheduleItemEntity

class AddScheduleItemUseCase(private val repository: ScheduleRepository) {
    suspend fun addScheduleItem(item: ScheduleItemEntity) {
        return repository.addScheduleItem(item)
    }
}