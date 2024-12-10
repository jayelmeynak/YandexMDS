package com.example.yandexmds.domain.useCases.task

import com.example.yandexmds.domain.repository.ToDoListRepository
import com.example.yandexmds.domain.model.ToDoItemEntity

class AddToDoUseCase(private val repository: ToDoListRepository) {

    suspend fun addToDo(item: ToDoItemEntity){
        return repository.addToDo(item)
    }
}