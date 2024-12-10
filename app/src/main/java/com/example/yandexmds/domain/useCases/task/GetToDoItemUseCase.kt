package com.example.yandexmds.domain.useCases.task

import com.example.yandexmds.domain.ToDoListRepository
import com.example.yandexmds.domain.model.ToDoItemEntity

class GetToDoItemUseCase(private val repository: ToDoListRepository) {

    suspend fun getToDo(id: Int): ToDoItemEntity{
        return repository.getToDo(id)
    }
}