package com.example.yandexmds.presentation.screens.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexmds.presentation.AddTopBar
import com.example.yandexmds.presentation.ScheduleTimePicker
import com.example.yandexmds.presentation.navigation.BottomNavigationBar
import com.example.yandexmds.presentation.navigation.Screen
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeParseException

@Composable
fun AddEditScheduleScreen(
    id: Int?,
    navController: NavController,
    selectedItem: MutableState<Int>,
    navigationItems: List<String>,
    unselectedNavigationIcons: List<ImageVector>,
    selectedNavigationIcons: List<ImageVector>
) {
    val viewModel: ScheduleViewModel = viewModel()

    val scheduleItem = viewModel.scheduleItem.observeAsState()
    val isScheduleItemLoading = viewModel.isScheduleItemLoading

    val subject = rememberSaveable { mutableStateOf("") }
    val dayOfWeek = rememberSaveable { mutableStateOf("Понедельник") }
    val startTime = rememberSaveable { mutableStateOf<LocalTime?>(null) }
    val endTime = rememberSaveable { mutableStateOf<LocalTime?>(null) }
    val teacher = rememberSaveable { mutableStateOf("") }
    val room = rememberSaveable { mutableStateOf("") }
    val color = remember { mutableStateOf(Color.Blue) }

    val startTimeDialogState = rememberMaterialDialogState()
    val endTimeDialogState = rememberMaterialDialogState()

    LaunchedEffect(id) {
        if (id != null) {
            viewModel.getScheduleItem(id)
        }
    }

    LaunchedEffect(scheduleItem.value) {
        scheduleItem.value?.let {
            subject.value = it.subject
            dayOfWeek.value = it.dayOfWeek
            startTime.value = it.startTime.toLocalTime()
            endTime.value = it.endTime.toLocalTime()
            teacher.value = it.teacher
            room.value = it.room
            color.value = Color(it.color)
        }
    }

    if (isScheduleItemLoading.value) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        Scaffold(
            topBar = {
                AddTopBar(
                    onCancelClickListener = {
                        navController.popBackStack()
                    },
                    onSaveClickListener = {
                        if (id == null) {
                            viewModel.addScheduleItem(
                                subject = subject.value,
                                dayOfWeek = dayOfWeek.value,
                                startTime = startTime.value.toTimeString(),
                                endTime = endTime.value.toTimeString(),
                                teacher = teacher.value,
                                room = room.value,
                                color = color.value.toArgb()
                            )
                        } else {
                            viewModel.updateScheduleItem(
                                subject = subject.value,
                                dayOfWeek = dayOfWeek.value,
                                startTime = startTime.value.toTimeString(),
                                endTime = endTime.value.toTimeString(),
                                teacher = teacher.value,
                                room = room.value,
                                color = color.value.toArgb()
                            )
                        }
                        navController.navigateUp()
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    items = navigationItems,
                    selectedItem = selectedItem.value,
                    unselectedIcons = unselectedNavigationIcons,
                    selectedIcons = selectedNavigationIcons
                ) {
                    selectedItem.value = it
                    if (it == 0) {
                        navController.navigate(Screen.TasksMain.route)
                    } else {
                        navController.navigate(Screen.ScheduleMain.route)
                    }
                }
            }

        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = subject.value,
                    onValueChange = { subject.value = it },
                    label = null,
                    textStyle = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = dayOfWeek.value,
                    onValueChange = { dayOfWeek.value = it },
                    label = null,
                    textStyle = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                ScheduleTimePicker(
                    isStartTime = true,
                    timeDialogState = startTimeDialogState,
                    pickedTime = startTime
                )

                Spacer(modifier = Modifier.height(8.dp))

                ScheduleTimePicker(
                    isStartTime = false,
                    timeDialogState = endTimeDialogState,
                    pickedTime = endTime
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = teacher.value,
                    onValueChange = { teacher.value = it },
                    label = null,
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = room.value,
                    onValueChange = { room.value = it },
                    label = null,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Цвет",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}

fun LocalTime?.toTimeString(): String {
    return this?.toString() ?: ""
}

fun String.toLocalTime(): LocalTime? {
    return try {
        LocalTime.parse(this)
    } catch (e: DateTimeParseException) {
        null
    }
}