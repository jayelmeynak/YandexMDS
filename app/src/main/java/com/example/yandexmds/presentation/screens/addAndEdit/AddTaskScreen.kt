package com.example.yandexmds.presentation.screens.addAndEdit

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexmds.R
import com.example.yandexmds.domain.model.Significance
import com.example.yandexmds.presentation.screens.main.MainViewModel
import com.example.yandexmds.ui.theme.Blue
import com.example.yandexmds.ui.theme.DarkOverlayColor
import com.example.yandexmds.ui.theme.Gray
import com.example.yandexmds.ui.theme.Red
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AddScreen(id: Int?, navController: NavController) {
    val viewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)
    val task = viewModel.task.value
    var text by remember { mutableStateOf(("")) }
    val scrollState = rememberScrollState()
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(Significance.USUAL) }
    val checked = remember {
        mutableStateOf(false)
    }
    val pickedDate = remember {
        mutableStateOf<LocalDate?>(null)
    }


    LaunchedEffect(id) {
        if (id != null) {
            viewModel.getTask(id)
            text = viewModel.task.value?.description ?: ""
            selectedOption.value = viewModel.task.value?.significance ?: Significance.USUAL
            val deadline = viewModel.task.value?.deadline
            if (deadline != null) {
                pickedDate.value = stringToLocalDate(deadline)
                checked.value = true
            }
        }
    }
    val dateDialogState = rememberMaterialDialogState()
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
                                DateTimeFormatter
                                    .ofPattern("dd MMM yyyy")
                                    .format(pickedDate.value)
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
                                DateTimeFormatter
                                    .ofPattern("dd MMM yyyy")
                                    .format(pickedDate.value)
                            } else {
                                null

                            }
                        )
                    }
                    navController.navigateUp()
                }
            )
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
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(color = MaterialTheme.colorScheme.tertiary),
                    minLines = 3,
                    maxLines = 11,
                    placeholder = {
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = "Что надо сделать…",
                            style = MaterialTheme.typography.bodyMedium)
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
                    onDeleteClickListener = {
                        if (id == null) {
                            navController.navigateUp()
                        } else {
                            if (task != null) {
                                viewModel.deleteTask(task)
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
            Box {
                TextButton(
                    onClick = { expanded.value = true },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Важность",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
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
            Text(
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
        offset = DpOffset(0.dp, (-36).dp),
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
    checked: MutableState<Boolean>
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

    }
}

@Composable
fun RowDelete(
    onDeleteClickListener: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        TextButton(
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
                    tint = Red
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    modifier = Modifier.clickable {
                        /*TODO*/
                    },
                    text = "Удалить",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Red)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTopBar(
    onCancelClickListener: () -> Unit,
    onSaveClickListener: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onCancelClickListener()
            }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            Button(onClick = {
                onSaveClickListener()
            }) {
                Text(text = "Сохранить", color = Blue, style = MaterialTheme.typography.titleSmall)
            }
        },
        title = { }
    )
}

fun stringToLocalDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("ru"))
    return LocalDate.parse(dateString, formatter)
}
