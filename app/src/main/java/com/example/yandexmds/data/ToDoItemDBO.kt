package com.example.yandexmds.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yandexmds.domain.model.Significance

@Entity
data class ToDoItemDBO(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String,
    val significance: Significance = Significance.USUAL,
    val achievement: Boolean,
    val deadline: String?
)