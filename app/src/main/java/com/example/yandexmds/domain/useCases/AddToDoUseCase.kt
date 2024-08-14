package com.example.yandexmds.domain.useCases

import com.example.yandexmds.domain.ToDoListRepository
import com.example.yandexmds.domain.model.ToDoItemEntity

class AddToDoUseCase(private val repository: ToDoListRepository) {

    suspend fun addToDo(item: ToDoItemEntity){
        return repository.addToDo(item)
    }
}