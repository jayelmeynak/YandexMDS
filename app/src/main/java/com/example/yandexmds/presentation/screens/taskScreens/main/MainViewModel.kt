package com.example.yandexmds.presentation.screens.taskScreens.main

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yandexmds.data.ToDoListRepositoryImpl
import com.example.yandexmds.domain.model.Significance
import com.example.yandexmds.domain.model.ToDoItemEntity
import com.example.yandexmds.domain.useCases.task.AddToDoUseCase
import com.example.yandexmds.domain.useCases.task.DeleteToDoUseCase
import com.example.yandexmds.domain.useCases.task.EditToDoUseCase
import com.example.yandexmds.domain.useCases.task.GetToDoItemUseCase
import com.example.yandexmds.domain.useCases.task.GetToDoListFilterByAchievementUseCase
import com.example.yandexmds.domain.useCases.task.GetToDoListUseCase
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
    private val getTaskListFilterByAchievement = GetToDoListFilterByAchievementUseCase(repository)

    val allTasksList = getTaskList.getToDoList()
    val filterTasksList = getTaskListFilterByAchievement.getFilteredTaskList()

    private val _isFilter = MutableLiveData<Boolean>(false)
    val isFilter: LiveData<Boolean>
        get() = _isFilter

    private val _task = MutableLiveData<ToDoItemEntity>()
    val task: LiveData<ToDoItemEntity>
        get() = _task

    // Состояние для отслеживания статуса загрузки
    private val _isTaskLoaded = MutableStateFlow(false)
    val isTaskLoaded: StateFlow<Boolean> = _isTaskLoaded.asStateFlow()

    val countCompletedTask = mutableStateOf(0)

    init {
        allTasksList.observeForever {
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
                    edited = null,
                    deadline = deadline,
                    scheduleId = null
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

    fun changeFilter() {
        _isFilter.value?.let {
            _isFilter.value = !it
        }
    }

    fun calculateCompletedTask() {
        countCompletedTask.value = 0
        allTasksList.value?.forEach { task ->
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