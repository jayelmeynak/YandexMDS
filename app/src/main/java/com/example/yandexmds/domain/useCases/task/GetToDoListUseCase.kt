package com.example.yandexmds.domain.useCases.task

import androidx.lifecycle.LiveData
import com.example.yandexmds.domain.repository.ToDoListRepository
import com.example.yandexmds.domain.model.ToDoItemEntity

class GetToDoListUseCase(private val repository: ToDoListRepository) {

    fun getToDoList(): LiveData<List<ToDoItemEntity>> {
        return repository.getToDoList()
    }
}