package com.example.yandexmds.domain.useCases.schedule

import com.example.yandexmds.domain.repository.ScheduleRepository

class DeleteAllScheduleItemsUseCase(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke() {
        scheduleRepository.deleteAllScheduleItems()
    }
}