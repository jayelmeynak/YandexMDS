package com.example.yandexmds.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yandexmds.R
import com.example.yandexmds.ui.theme.Blue
import com.example.yandexmds.ui.theme.Red
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTopBar(
    isEditScreen: Boolean,
    onCancelClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onDeleteClickListener: () -> Unit
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
            Button(
                colors = ButtonDefaults.buttonColors()
                    .copy(containerColor = MaterialTheme.colorScheme.background),
                onClick = {
                    onSaveClickListener()
                }) {
                Text(text = "Сохранить", color = Blue, style = MaterialTheme.typography.titleSmall)
            }
            if (isEditScreen) {
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = MaterialTheme.colorScheme.background),
                    onClick = {
                        onDeleteClickListener()
                    }) {
                    Text(text = "Удалить", color = Red, style = MaterialTheme.typography.titleSmall)
                }
            }
        },
        title = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleMainTopBar(
    onDeleteClickListener: () -> Unit
){
    TopAppBar(
        title = {
            Text(
                text = "Расписание",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            IconButton(
                onClick = {
                    onDeleteClickListener()
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null,
                    tint = Red
                )
            }
        }
    )
}


@Composable
fun ScheduleTimePicker(
    modifier: Modifier = Modifier,
    isStartTime: Boolean,
    timeDialogState: MaterialDialogState,
    pickedTime: MutableState<LocalTime?>,
) {

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(if (isStartTime) "Начало" else "Конец")
                Text(
                    text = "*",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(":")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(
                onClick = { timeDialogState.show() }) {
                Text(
                    text = pickedTime.value?.toString() ?: "Выбрать время",
                )
            }
        }

    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(
                text = "Готово",
                textStyle = MaterialTheme.typography.labelMedium.copy(color = Blue)
            )
            negativeButton(
                text = "Отмена",
                textStyle = MaterialTheme.typography.labelMedium.copy(color = Blue)
            )
        }
    ) {
        timepicker(
            initialTime = LocalTime.MIDNIGHT,
            colors = TimePickerDefaults.colors(),
            is24HourClock = true
        ) { time ->
            pickedTime.value = time
        }
    }
}

@Composable
fun ColorPickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onColorSelected: (Color) -> Unit
) {
    if (showDialog) {
        var colorChoose by remember { mutableStateOf(Color.Red) }
        val controller = rememberColorPickerController()

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Выберите цвет") },
            text = {
                Column {
                    AlphaTile(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        controller = controller
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(10.dp),
                        controller = controller,
                        onColorChanged = {
                            colorChoose = it.color
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onColorSelected(colorChoose)
                    onDismiss()
                }) {
                    Text("Сохранить", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Отменить", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
