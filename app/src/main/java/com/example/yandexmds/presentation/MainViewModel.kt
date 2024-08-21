package com.example.yandexmds.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yandexmds.data.ToDoListRepositoryImpl
import com.example.yandexmds.domain.model.Significance
import com.example.yandexmds.domain.model.ToDoItemEntity
import com.example.yandexmds.domain.useCases.AddToDoUseCase
import com.example.yandexmds.domain.useCases.DeleteToDoUseCase
import com.example.yandexmds.domain.useCases.EditToDoUseCase
import com.example.yandexmds.domain.useCases.GetToDoItemUseCase
import com.example.yandexmds.domain.useCases.GetToDoListUseCase
import kotlinx.coroutines.launch
import java.util.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ToDoListRepositoryImpl(application)
    private val addTask = AddToDoUseCase(repository)
    private val editTask = EditToDoUseCase(repository)
    private val deleteTask = DeleteToDoUseCase(repository)
    private val getTask = GetToDoItemUseCase(repository)
    private val getTaskList = GetToDoListUseCase(repository)

    val random = Random()

    val list = List(30) { index ->
        ToDoItemEntity(
            id = index,
            description = "Купить что-то",
            significance = if(random.nextBoolean()) Significance.USUAL else Significance.HIGH,
            achievement = random.nextBoolean(),
            deadline = null
        )
    }
    val taskList = MutableLiveData(list)
    //getTaskList.getToDoList()

    private val _task = MutableLiveData<ToDoItemEntity>()
    val task: LiveData<ToDoItemEntity>
        get() = _task

    fun addTask(
        description: String,
        significance: Significance,
        achievement: Boolean,
        deadline: String
    ) {
        if (description.isNotEmpty()) {
            val task = ToDoItemEntity(
                id = UNDEFINED_ID,
                description = description,
                significance = significance,
                achievement = achievement,
                deadline = deadline
            )
            viewModelScope.launch {
                addTask.addToDo(task)
            }
        }
    }

    fun changeAchievement(task: ToDoItemEntity) {
        val newTask = task.copy(achievement = !task.achievement)
        viewModelScope.launch {
            editTask.editToDo(newTask)
        }
    }

    fun editTask(
        description: String,
        significance: Significance,
        achievement: Boolean,
        deadline: String
    ) {
        if (description.isNotEmpty()) {
            _task.value?.let {
                val task = it.copy(
                    description = description,
                    significance = significance,
                    achievement = achievement,
                    deadline = deadline
                )
                viewModelScope.launch {
                    editTask.editToDo(task)
                }
            }
        }
    }

    fun deleteTask(task: ToDoItemEntity) {
        viewModelScope.launch {
            deleteTask.deleteToDo(task)
        }
    }

    fun getTask(id: Int) {
        viewModelScope.launch {
            val task = getTask.getToDo(id)
            _task.value = task
        }
    }

    companion object {
        const val UNDEFINED_ID = 0
    }

}