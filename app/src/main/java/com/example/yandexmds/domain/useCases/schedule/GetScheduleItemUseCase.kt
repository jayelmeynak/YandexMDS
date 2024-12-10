package com.example.yandexmds.domain.useCases.schedule

import com.example.yandexmds.domain.model.ScheduleItemEntity

class GetScheduleItemUseCase(private val repository: ScheduleRepository) {
    suspend fun getScheduleItem(id: Int): ScheduleItemEntity {
        return repository.getScheduleItem(id)
    }
}