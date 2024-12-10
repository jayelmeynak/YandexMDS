package com.example.yandexmds.domain.useCases.schedule

import com.example.yandexmds.domain.model.ScheduleItemEntity
import com.example.yandexmds.domain.repository.ScheduleRepository

class DeleteScheduleItemUseCase(private val repository: ScheduleRepository) {
    suspend fun deleteScheduleItem(item: ScheduleItemEntity) {
        return repository.deleteScheduleItem(item)
    }
}