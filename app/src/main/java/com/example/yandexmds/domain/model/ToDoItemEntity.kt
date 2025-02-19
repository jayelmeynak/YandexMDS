package com.example.yandexmds.domain.model

data class ToDoItemEntity(
    val id: Int,
    val description: String,
    val significance: Significance = Significance.USUAL,
    val achievement: Boolean,
    val created: String,
    val edited: String?,
    val deadline: String?,
    val scheduleId: Long?,
)