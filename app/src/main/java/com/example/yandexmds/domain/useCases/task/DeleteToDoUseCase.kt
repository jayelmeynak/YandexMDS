package com.example.yandexmds.domain.useCases.task

import com.example.yandexmds.domain.ToDoListRepository
import com.example.yandexmds.domain.model.ToDoItemEntity

class DeleteToDoUseCase(private val repository: ToDoListRepository) {
    suspend fun deleteToDo(item: ToDoItemEntity){
        return repository.deleteToDo(item)
    }
}