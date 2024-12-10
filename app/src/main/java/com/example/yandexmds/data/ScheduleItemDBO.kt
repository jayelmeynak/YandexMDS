package com.example.yandexmds.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScheduleItemDBO(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val subject: String,
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String,
    val teacher: String,
    val room: String,
    val color: Int,
)

