package com.example.yandexmds.domain.useCases

import androidx.lifecycle.LiveData
import com.example.yandexmds.domain.ToDoListRepository
import com.example.yandexmds.domain.model.ToDoItemEntity

class GetToDoListUseCase(private val repository: ToDoListRepository) {

    fun getToDoList(): LiveData<List<ToDoItemEntity>> {
        return repository.getToDoList()
    }
}