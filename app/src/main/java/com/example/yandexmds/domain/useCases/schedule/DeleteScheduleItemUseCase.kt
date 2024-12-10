package com.example.yandexmds.domain.useCases.schedule

import com.example.yandexmds.domain.model.ScheduleItemEntity

class DeleteScheduleItemUseCase(private val repository: ScheduleRepository) {
    suspend fun deleteScheduleItem(item: ScheduleItemEntity) {
        return repository.deleteScheduleItem(item)
    }
}