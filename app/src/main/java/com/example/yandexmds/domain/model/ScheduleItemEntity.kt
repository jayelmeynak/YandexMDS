package com.example.yandexmds.domain.model

data class ScheduleItemEntity(
    val id: Int,
    val subject: String,
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String,
    val teacher: String,
    val room: String,
    val color: Int
)

