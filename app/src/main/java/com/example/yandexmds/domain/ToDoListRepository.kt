package com.example.yandexmds.domain

import androidx.lifecycle.LiveData
import com.example.yandexmds.domain.model.ToDoItemEntity

interface ToDoListRepository {
    suspend fun addToDo(item: ToDoItemEntity)
    suspend fun deleteToDo(item: ToDoItemEntity)
    suspend fun editToDo(item: ToDoItemEntity)
    suspend fun getToDo(id: Int): ToDoItemEntity
    fun getToDoListFilteredByAchievement(): LiveData<List<ToDoItemEntity>>
    fun getToDoList(): LiveData<List<ToDoItemEntity>>
}