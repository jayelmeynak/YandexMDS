package com.example.yandexmds.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yandexmds.R
import com.example.yandexmds.ui.theme.Blue
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalTime

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
            Button(
                colors = ButtonDefaults.buttonColors()
                    .copy(containerColor = MaterialTheme.colorScheme.background),
                onClick = {
                    onSaveClickListener()
                }) {
                Text(text = "Сохранить", color = Blue, style = MaterialTheme.typography.titleSmall)
            }
        },
        title = { }
    )
}



@Composable
fun ScheduleTimePicker(
    isStartTime: Boolean,
    timeDialogState: MaterialDialogState,
    pickedTime: MutableState<LocalTime?>,
) {

    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
    ) {
        Button(
            onClick = { timeDialogState.show() }) {
            Text(
                text = pickedTime.value?.toString() ?: if (isStartTime) "Начало" else "Конец",
                modifier = Modifier.fillMaxWidth(),
            )
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
            colors = TimePickerDefaults.colors()
        ) { time ->
            pickedTime.value = time
        }
    }
}
