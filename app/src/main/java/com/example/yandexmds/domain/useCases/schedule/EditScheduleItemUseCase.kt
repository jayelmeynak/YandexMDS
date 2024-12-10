package com.example.yandexmds.domain.useCases.schedule

import com.example.yandexmds.domain.model.ScheduleItemEntity
import com.example.yandexmds.domain.repository.ScheduleRepository

class EditScheduleItemUseCase(private val repository: ScheduleRepository) {
    suspend fun editScheduleItem(item: ScheduleItemEntity) {
        return repository.editScheduleItem(item)
    }
}