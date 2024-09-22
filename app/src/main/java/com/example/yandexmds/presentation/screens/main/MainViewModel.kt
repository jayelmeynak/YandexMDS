package com.example.yandexmds.presentation.screens.main

import android.app.Application
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ToDoListRepositoryImpl(application)
    private val addTask = AddToDoUseCase(repository)
    private val editTask = EditToDoUseCase(repository)
    private val deleteTask = DeleteToDoUseCase(repository)
    private val getTask = GetToDoItemUseCase(repository)
    private val getTaskList = GetToDoListUseCase(repository)

    val taskList = getTaskList.getToDoList()

    private val _task = MutableLiveData<ToDoItemEntity>()
    val task: LiveData<ToDoItemEntity>
        get() = _task

    // Состояние для отслеживания статуса загрузки
    private val _isTaskLoaded = MutableStateFlow(false)
    val isTaskLoaded: StateFlow<Boolean> = _isTaskLoaded.asStateFlow()

    val countCompletedTask = mutableStateOf(0)

    init {
        taskList.observeForever {
            calculateCompletedTask()
        }
    }

    fun addTask(
        description: String,
        significance: Significance,
        achievement: Boolean,
        deadline: String?
    ) {
        if (description.isNotEmpty()) {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
            val formattedDateTime = currentDateTime.format(formatter)
            val task =
                ToDoItemEntity(
                    id = UNDEFINED_ID,
                    description = description,
                    significance = significance,
                    achievement = achievement,
                    created = formattedDateTime,
                    edited = "",
                    deadline = deadline
                )
            viewModelScope.launch {
                addTask.addToDo(task)
            }
        }
        calculateCompletedTask()
    }

    fun changeAchievement(task: ToDoItemEntity) {
        viewModelScope.launch {
            var newTask = getTask.getToDo(task.id)
            newTask = newTask.copy(achievement = !newTask.achievement)
            editTask.editToDo(newTask)
        }
        calculateCompletedTask()
    }

    fun editTask(
        description: String,
        significance: Significance,
        achievement: Boolean,
        deadline: String?
    ) {
        if (description.isNotEmpty()) {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
            val formattedDateTime = currentDateTime.format(formatter)
            _task.value?.let {
                val task = it.copy(
                    description = description,
                    significance = significance,
                    achievement = achievement,
                    edited = formattedDateTime,
                    deadline = deadline
                )
                viewModelScope.launch {
                    editTask.editToDo(task)
                }
            }
        }
        calculateCompletedTask()
    }

    fun deleteTask(task: ToDoItemEntity) {
        viewModelScope.launch {
            deleteTask.deleteToDo(task)
        }
        calculateCompletedTask()
    }

    fun getTask(id: Int) {
        viewModelScope.launch {
            val task = getTask.getToDo(id)
            _task.value = task
            _isTaskLoaded.value = true
        }
    }

    fun calculateCompletedTask() {
        countCompletedTask.value = 0
        taskList.value?.forEach { task ->
            if (task.achievement) countCompletedTask.value += 1
        }
    }

    fun changeLoaded() {
        _isTaskLoaded.value = false
    }

    companion object {
        const val UNDEFINED_ID = 0
    }

}