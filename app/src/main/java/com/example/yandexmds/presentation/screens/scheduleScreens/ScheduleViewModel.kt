package com.example.yandexmds.presentation.screens.scheduleScreens

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.yandexmds.data.ScheduleRepositoryImpl
import com.example.yandexmds.domain.model.ScheduleItemEntity
import com.example.yandexmds.domain.model.Weekday
import com.example.yandexmds.domain.useCases.schedule.AddScheduleItemUseCase
import com.example.yandexmds.domain.useCases.schedule.DeleteAllScheduleItemsUseCase
import com.example.yandexmds.domain.useCases.schedule.DeleteScheduleItemUseCase
import com.example.yandexmds.domain.useCases.schedule.EditScheduleItemUseCase
import com.example.yandexmds.domain.useCases.schedule.GetScheduleItemUseCase
import com.example.yandexmds.domain.useCases.schedule.GetScheduleListUseCase
import com.example.yandexmds.ui.theme.Gray
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ScheduleRepositoryImpl(application)
    private val addScheduleItemUseCase = AddScheduleItemUseCase(repository)
    private val deleteScheduleItemUseCase = DeleteScheduleItemUseCase(repository)
    private val updateScheduleItemUseCase = EditScheduleItemUseCase(repository)
    private val getScheduleItemUseCase = GetScheduleItemUseCase(repository)
    private val getScheduleListUseCase = GetScheduleListUseCase(repository)
    private val deleteAllScheduleItemsUseCase = DeleteAllScheduleItemsUseCase(repository)

    private val _isScheduleItemLoading = mutableStateOf(false)
    val isScheduleItemLoading: State<Boolean>
        get() = _isScheduleItemLoading

    private val _scheduleItem = MutableLiveData<ScheduleItemEntity>()
    val scheduleItem: LiveData<ScheduleItemEntity>
        get() = _scheduleItem

    private val _isListLoading = mutableStateOf(false)
    val isListLoading: State<Boolean>
        get() = _isListLoading

    private val scheduleListWithOutGroup: LiveData<List<ScheduleItemEntity>> =
        getScheduleListUseCase.getScheduleList()

    val daysOfWeek = listOf(
        "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"
    )

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String>
        get() = _errorMessage

    val groupedScheduleList: LiveData<Map<String, List<ScheduleItemEntity>>> =
        scheduleListWithOutGroup.map { scheduleList ->
            scheduleList
                .sortedBy { daysOfWeek.indexOf(it.dayOfWeek.name) }
                .groupBy { it.dayOfWeek.name }
        }

    init {
        _isListLoading.value = true
        scheduleListWithOutGroup.observeForever {
            _isListLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        scheduleListWithOutGroup.removeObserver {}
    }

    fun addScheduleItem(
        subject: String,
        room: String?,
        dayOfWeek: Weekday,
        startTime: String,
        endTime: String,
        teacher: String?,
        scheduleType: String?,
        color: Int?
    ) {
        if (subject.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty()) {

            val scheduleItemEntity = ScheduleItemEntity(
                id = UNDEFINED_ID,
                subject = subject,
                dayOfWeek = dayOfWeek,
                startTime = startTime,
                endTime = endTime,
                teacher = teacher,
                room = room,
                color = color ?: Gray.toArgb(),
                scheduleType = scheduleType
            )
            _errorMessage.value = ""
            viewModelScope.launch {
                addScheduleItemUseCase.addScheduleItem(scheduleItemEntity)
            }
        } else {
            _errorMessage.value = "Заполните все обязательные поля"
        }
    }

    fun deleteScheduleItem(scheduleItemEntity: ScheduleItemEntity) {
        viewModelScope.launch {
            deleteScheduleItemUseCase.deleteScheduleItem(scheduleItemEntity)
        }
    }

    fun updateScheduleItem(
        subject: String,
        dayOfWeek: Weekday,
        startTime: String,
        endTime: String,
        teacher: String?,
        room: String?,
        scheduleType: String?,
        color: Int?
    ) {
        if (subject.isNotEmpty()  && startTime.isNotEmpty() && endTime.isNotEmpty()) {
            _errorMessage.value = ""
            _scheduleItem.value?.let {
                val scheduleItemEntity = it.copy(
                    subject = subject,
                    dayOfWeek = dayOfWeek,
                    startTime = startTime,
                    endTime = endTime,
                    teacher = teacher,
                    room = room,
                    color = color ?: Gray.toArgb(),
                    scheduleType = scheduleType
                )
                viewModelScope.launch {
                    updateScheduleItemUseCase.editScheduleItem(scheduleItemEntity)
                }
            }
        } else {
            _errorMessage.value = "Заполните все обязательные поля"
        }

    }

    fun getScheduleItem(id: Int) {
        viewModelScope.launch {
            _isScheduleItemLoading.value = true
            _scheduleItem.value = getScheduleItemUseCase.getScheduleItem(id)
            _isScheduleItemLoading.value = false
        }
    }

    fun deleteAllScheduleItems() {
        viewModelScope.launch {
            deleteAllScheduleItemsUseCase()
        }
    }


    companion object {
        const val UNDEFINED_ID = 0
    }
}