package com.example.yandexmds.domain.useCases.task

import androidx.lifecycle.LiveData
import com.example.yandexmds.domain.ToDoListRepository
import com.example.yandexmds.domain.model.ToDoItemEntity

class GetToDoListFilterByAchievementUseCase (private val repository: ToDoListRepository) {

    fun getFilteredTaskList(): LiveData<List<ToDoItemEntity>> {
        return repository.getToDoListFilteredByAchievement()
    }
}