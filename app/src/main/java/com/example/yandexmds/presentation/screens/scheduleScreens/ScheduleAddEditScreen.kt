package com.example.yandexmds.presentation.screens.scheduleScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexmds.domain.model.Weekday
import com.example.yandexmds.presentation.AddTopBar
import com.example.yandexmds.presentation.ColorPickerDialog
import com.example.yandexmds.presentation.ScheduleTimePicker
import com.example.yandexmds.presentation.navigation.BottomNavigationBar
import com.example.yandexmds.presentation.navigation.Screen
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
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
    val room = rememberSaveable { mutableStateOf<String?>(null) }
    val dayOfWeek = remember { mutableStateOf<Weekday>(Weekday.Monday) }
    val startTime = rememberSaveable { mutableStateOf<LocalTime?>(null) }
    val endTime = rememberSaveable { mutableStateOf<LocalTime?>(null) }
    val teacher = rememberSaveable { mutableStateOf<String?>(null) }
    val scheduleType = rememberSaveable { mutableStateOf<String?>(null) }
    val color = remember { mutableStateOf<Color?>(null) }

    val expanded = remember { mutableStateOf(false) }
    val showColorPicker = remember { mutableStateOf(false) }

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
            color.value = it.color?.let { it1 -> Color(it1) }
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
                                room = room.value,
                                dayOfWeek = dayOfWeek.value,
                                startTime = startTime.value.toTimeString(),
                                endTime = endTime.value.toTimeString(),
                                teacher = teacher.value,
                                scheduleType = scheduleType.value,
                                color = color.value?.toArgb()
                            )
                        } else {
                            viewModel.updateScheduleItem(
                                subject = subject.value,
                                room = room.value,
                                dayOfWeek = dayOfWeek.value,
                                startTime = startTime.value.toTimeString(),
                                endTime = endTime.value.toTimeString(),
                                teacher = teacher.value,
                                scheduleType = scheduleType.value,
                                color = color.value?.toArgb()
                            )
                        }
                        if (viewModel.errorMessage.value.isEmpty()) {
                            navController.navigateUp()
                        }

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
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    value = subject.value,
                    onValueChange = { subject.value = it },
                    label = {
                        Text("Предмет", color = Color.Gray)
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                if (subject.value.isEmpty()) {
                    Text(
                        text = "Это поле обязательно для заполнения",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    value = room.value ?: "",
                    label = {
                        Text("Аудитория", color = Color.Gray)
                    },
                    onValueChange = { room.value = it },
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = { expanded.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                        value = dayOfWeek.value.name,
                        onValueChange = { },
                        label = {
                            Text("День недели", color = Color.Gray)
                        },
                        textStyle = MaterialTheme.typography.bodyMedium,
                        readOnly = true,
                    )

                    DropMenuWeekday(
                        expanded = expanded.value,
                        onSelectedClickListener = {
                            dayOfWeek.value = it
                        },
                        onDismissClickListener = {
                            expanded.value = it
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ScheduleTimePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    isStartTime = true,
                    timeDialogState = startTimeDialogState,
                    pickedTime = startTime
                )

                Spacer(modifier = Modifier.height(8.dp))

                ScheduleTimePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    isStartTime = false,
                    timeDialogState = endTimeDialogState,
                    pickedTime = endTime
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    value = teacher.value ?: "",
                    label = {
                        Text("Преподаватель", color = Color.Gray)
                    },
                    onValueChange = { teacher.value = it },
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    value = scheduleType.value ?: "",
                    label = {
                        Text("Тип занятия", color = Color.Gray)
                    },
                    onValueChange = { scheduleType.value = it },
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text("Цвет")
                    Button(onClick = { showColorPicker.value = true }) {
                        if (color.value != null) {
                            Text("Выбрать цвет", color = color.value!!)
                        } else {
                            Text("Выбрать цвет")
                        }
                    }
                }

                ColorPickerDialog(
                    showDialog = showColorPicker.value,
                    onDismiss = { showColorPicker.value = false },
                    onColorSelected = { selectedColor ->
                        color.value = selectedColor
                    }
                )
            }
        }
    }
}


@Composable
fun DropMenuWeekday(
    expanded: Boolean,
    onSelectedClickListener: (Weekday) -> Unit,
    onDismissClickListener: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissClickListener(false) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(Weekday.Monday)
                onDismissClickListener(false)
            },
            text = { Text(Weekday.Monday.name) }
        )
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(Weekday.Tuesday)
                onDismissClickListener(false)
            },
            text = { Text(Weekday.Tuesday.name) }
        )
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(Weekday.Wednesday)
                onDismissClickListener(false)
            },
            text = { Text(Weekday.Wednesday.name) }
        )
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(Weekday.Thursday)
                onDismissClickListener(false)
            },
            text = { Text(Weekday.Thursday.name) }
        )
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(Weekday.Friday)
                onDismissClickListener(false)
            },
            text = { Text(Weekday.Friday.name) }
        )
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(Weekday.Saturday)
                onDismissClickListener(false)
            },
            text = { Text(Weekday.Saturday.name) }
        )
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