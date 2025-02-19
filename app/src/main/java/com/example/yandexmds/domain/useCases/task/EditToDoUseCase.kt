package com.example.yandexmds.domain.useCases.task

import com.example.yandexmds.domain.repository.ToDoListRepository
import com.example.yandexmds.domain.model.ToDoItemEntity

class EditToDoUseCase(private val repository: ToDoListRepository) {

    suspend fun editToDo(item: ToDoItemEntity){
        return repository.editToDo(item)
    }
}