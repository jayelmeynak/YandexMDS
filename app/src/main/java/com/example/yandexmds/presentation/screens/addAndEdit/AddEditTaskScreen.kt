package com.example.yandexmds.presentation.screens.addAndEdit

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexmds.R
import com.example.yandexmds.domain.model.Significance
import com.example.yandexmds.presentation.AddTopBar
import com.example.yandexmds.presentation.navigation.BottomNavigationBar
import com.example.yandexmds.presentation.navigation.Screen
import com.example.yandexmds.presentation.screens.main.MainViewModel
import com.example.yandexmds.ui.theme.Blue
import com.example.yandexmds.ui.theme.DarkOverlayColor
import com.example.yandexmds.ui.theme.Gray
import com.example.yandexmds.ui.theme.Red
import com.example.yandexmds.ui.theme.White
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AddEditTaskScreen(
    id: Int?,
    navController: NavController,
    selectedItem: MutableState<Int>,
    navigationItems: List<String>,
    unselectedNavigationIcons: List<ImageVector>,
    selectedNavigationIcons: List<ImageVector>
) {
    val viewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)

    val task = viewModel.task.observeAsState()
    val isTaskLoaded by viewModel.isTaskLoaded.collectAsState()

    var text by rememberSaveable { mutableStateOf(("")) }
    val scrollState = rememberScrollState()
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(Significance.USUAL) }
    val checked = remember {
        mutableStateOf(false)
    }
    val pickedDate = remember {
        mutableStateOf<LocalDate?>(null)
    }

    val pickedTime = remember {
        mutableStateOf<LocalTime>(LocalTime.NOON)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(id) {
        if (id != null) {
            Log.d("MyLog", id.toString())
            viewModel.getTask(id)
        }
    }

    LaunchedEffect(isTaskLoaded) {
        if (isTaskLoaded && text.isEmpty()) {
            viewModel.task.value?.let { task ->
                text = task.description
                selectedOption.value = task.significance
                val deadline = task.deadline
                if (deadline != null) {
                    val dateTime = stringToLocalDateTime(deadline)
                    pickedDate.value = dateTime.toLocalDate()
                    pickedTime.value = dateTime.toLocalTime()
                    checked.value = true
                }
            }
            viewModel.changeLoaded()
        }
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    Scaffold(
        topBar = {
            AddTopBar(
                onCancelClickListener = {
                    navController.popBackStack()
                },
                onSaveClickListener = {

                    if (id == null) {

                        viewModel.addTask(
                            description = text,
                            significance = selectedOption.value,
                            achievement = false,
                            deadline =
                            if (checked.value) {
                                val formattedDateTime = pickedDate.value?.let { date ->
                                    val dateTime = LocalDateTime.of(date, pickedTime.value)
                                    DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
                                        .format(dateTime)
                                }
                                formattedDateTime
                            } else {
                                null

                            }
                        )
                    } else {
                        viewModel.editTask(
                            description = text,
                            significance = selectedOption.value,
                            achievement = false,
                            deadline =
                            if (checked.value) {
                                val formattedDateTime = pickedDate.value?.let { date ->
                                    val dateTime = LocalDateTime.of(date, pickedTime.value)
                                    DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
                                        .format(dateTime)
                                }
                                formattedDateTime
                            } else {
                                null

                            }
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
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                containerColor = Blue,
                onClick = {
                    if (id == null) {
                        viewModel.addTask(
                            description = text,
                            significance = selectedOption.value,
                            achievement = false,
                            deadline =
                            if (checked.value) {
                                val formattedDateTime = pickedDate.value?.let { date ->
                                    val dateTime = LocalDateTime.of(date, pickedTime.value)
                                    DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
                                        .format(dateTime)
                                }
                                formattedDateTime
                            } else {
                                null

                            }
                        )
                    } else {
                        viewModel.editTask(
                            description = text,
                            significance = selectedOption.value,
                            achievement = false,
                            deadline =
                            if (checked.value) {
                                val formattedDateTime = pickedDate.value?.let { date ->
                                    val dateTime = LocalDateTime.of(date, pickedTime.value)
                                    DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
                                        .format(dateTime)
                                }
                                formattedDateTime
                            } else {
                                null

                            }
                        )
                    }
                    navController.navigateUp()
                }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = null,
                    tint = White
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                focusManager.clearFocus()
                                keyboardController?.show()
                            }
                        )
                    }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    minLines = 3,
                    maxLines = 11,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors().copy(
                        focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = "Что надо сделать…",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    value = text,
                    onValueChange = {
                        text = it
                    }
                )

                RowImportance(
                    expanded = expanded,
                    selectedOption = selectedOption
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f))
                )

                RowDate(
                    pickedDate = pickedDate,
                    dateDialogState = dateDialogState,
                    pickedTime = pickedTime,
                    timeDialogState = timeDialogState,
                    checked = checked
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f))
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )

                RowDelete(
                    id = id,
                    onDeleteClickListener = {
                        if (id == null) {
                            navController.navigateUp()
                        } else {
                            if (task.value != null) {
                                viewModel.deleteTask(task.value!!)
                                navController.navigateUp()
                            }
                        }
                    }
                )

            }
        }
    }
}

@Composable
fun RowImportance(
    expanded: MutableState<Boolean>,
    selectedOption: MutableState<Significance>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Важность",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable {
                        expanded.value = true
                    }
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    text =
                    when (selectedOption.value) {
                        Significance.LOW -> "Низкий"
                        Significance.HIGH -> "!Высокий"
                        Significance.USUAL -> "Нет"
                    },
                    style = MaterialTheme.typography.titleSmall,
                    color = if (selectedOption.value == Significance.HIGH) {
                        Red
                    } else {
                        MaterialTheme.colorScheme.onTertiary
                    }
                )
                DropMenu(
                    expanded = expanded.value,
                    onSelectedClickListener = {
                        selectedOption.value = it
                    },
                    onDismissClickListener = {
                        expanded.value = it
                    }
                )
            }
        }

        if (selectedOption.value == Significance.HIGH) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector = Icons.Filled.PriorityHigh,
                contentDescription = null,
                tint = Red
            )
        }
    }
}

@Composable
fun DropMenu(
    expanded: Boolean,
    onSelectedClickListener: (Significance) -> Unit,
    onDismissClickListener: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissClickListener(false) },
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiary)
    ) {
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(Significance.USUAL)
                onDismissClickListener(false)
            },
            text = { Text("Нет") }
        )
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(Significance.LOW)
                onDismissClickListener(false)
            },
            text = { Text("Низкий") }
        )
        DropdownMenuItem(
            onClick = {
                onSelectedClickListener(Significance.HIGH)
                onDismissClickListener(false)
            },
            text = { Text("!Высокий", color = Red) }
        )
    }
}

@Composable
fun RowDate(
    pickedDate: MutableState<LocalDate?>,
    dateDialogState: MaterialDialogState,
    checked: MutableState<Boolean>,
    pickedTime: MutableState<LocalTime>,
    timeDialogState: MaterialDialogState
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    top = if (pickedDate.value == null) (26.5).dp else 16.dp,
                    bottom = if (pickedDate.value == null) (26.5).dp else 16.dp
                ),
            horizontalAlignment = Alignment.Start
        ) {
            TextButton(
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    dateDialogState.show()
                }) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = "Сделать до"
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            if (pickedDate.value != null && checked.value) {
                Text(
                    modifier = Modifier.clickable {
                        dateDialogState.show()
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color =
                    if (checked.value) {
                        Blue
                    } else {
                        Gray
                    },
                    text = DateTimeFormatter
                        .ofPattern("dd MMM yyyy")
                        .format(pickedDate.value)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.clickable {
                        timeDialogState.show()
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color =
                    if (checked.value) {
                        Blue
                    } else {
                        Gray
                    },
                    text = DateTimeFormatter
                        .ofPattern("HH:mm")
                        .format(pickedTime.value)
                )
            }
        }

        Switch(
            modifier = Modifier.size(36.dp, 20.dp),
            checked = checked.value,
            onCheckedChange = {
                checked.value = it
                if (it && pickedDate.value == null) {
                    dateDialogState.show()
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Blue,
                checkedTrackColor = Blue,
                checkedTrackAlpha = 0.3F,
                uncheckedThumbColor = MaterialTheme.colorScheme.tertiary,
                uncheckedTrackColor = DarkOverlayColor
            )
        )




        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(
                    text = "Готово",
                    textStyle = MaterialTheme.typography.labelMedium.copy(color = Blue)
                ) {
                    checked.value = true
                }
                negativeButton(
                    text = "Отмена",
                    textStyle = MaterialTheme.typography.labelMedium.copy(color = Blue)
                ) {
                    checked.value = false
                    pickedDate.value = null
                }
            }
        ) {
            datepicker(
                initialDate = LocalDate.now(),
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = Blue,
                    dateActiveBackgroundColor = Blue
                ),
                allowedDateValidator = {
                    it >= LocalDate.now()
                }
            ) {
                pickedDate.value = it
            }
        }

        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(
                    text = "Готово",
                    textStyle = MaterialTheme.typography.labelMedium.copy(color = Blue)
                ) {
                    checked.value = true
                }
                negativeButton(
                    text = "Отмена",
                    textStyle = MaterialTheme.typography.labelMedium.copy(color = Blue)
                ) {
                    pickedTime.value = LocalTime.NOON
                }
            }
        ) {
            timepicker(
                initialTime = LocalTime.NOON,
                is24HourClock = true,
                colors = TimePickerDefaults.colors(
                    selectorColor = Blue,
                    activeBackgroundColor = Blue
                )
            ) {
                pickedTime.value = it
            }
        }

    }
}

@Composable
fun RowDelete(
    id: Int?,
    onDeleteClickListener: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        TextButton(
            enabled = if (id == null) false else true,
            modifier = Modifier.padding(vertical = 12.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                onDeleteClickListener()
            }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    tint = if (id == null) Gray else Red
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    modifier = Modifier.clickable {
                        onDeleteClickListener()
                    },
                    text = "Удалить",
                    style = MaterialTheme.typography.bodyMedium.copy(color = if (id == null) Gray else Red)
                )
            }
        }
    }
}

fun stringToLocalDateTime(deadline: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
    return LocalDateTime.parse(deadline, formatter)
}
