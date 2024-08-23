package com.example.yandexmds.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.yandexmds.R
import com.example.yandexmds.domain.model.Significance
import com.example.yandexmds.domain.model.ToDoItemEntity
import com.example.yandexmds.ui.theme.Gray
import com.example.yandexmds.ui.theme.Green
import com.example.yandexmds.ui.theme.Red


@Composable
fun TaskItem(task: ToDoItemEntity,
             onCheckClickListener: (ToDoItemEntity) -> Unit) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Check(
            task = task,
            onCheckClickListener = onCheckClickListener
            )

        if (task.significance == Significance.USUAL) {
            Text(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .padding(start = 12.dp)
                    .weight(2F),
                text = task.description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration =
                    if (task.achievement) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )
            )
        }

        if (task.significance == Significance.HIGH) {
            Row(
                modifier = Modifier
                    .weight(2F)
                    .padding(start = 12.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                if (!task.achievement) {
                    Icon(
                        modifier = Modifier
                            .size(18.dp),
                        imageVector = Icons.Filled.PriorityHigh,
                        contentDescription = null,
                        tint = Red
                    )
                }
                TextDescription(task)
            }
        }
        if (task.significance == Significance.LOW) {
            Row(
                modifier = Modifier
                    .weight(2F)
                    .padding(start = 12.dp)
            ) {
                if (!task.achievement) {
                    Icon(
                        modifier = Modifier
                            .size(10.dp, 16.dp)
                            .padding(vertical = 3.dp, horizontal = 2.5.dp),
                        painter = painterResource(id = R.drawable.low), contentDescription = null
                    )
                }
                TextDescription(task)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.info_outline), contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary
        )
    }
}

@Composable
fun Check(
    task: ToDoItemEntity,
    onCheckClickListener: (ToDoItemEntity) -> Unit
) {
    val checked by remember { mutableStateOf(task.achievement) }
    val significance = task.significance
    Checkbox(
        modifier = Modifier
            .size(24.dp)
            .padding(3.dp),
        checked = checked,
        onCheckedChange = { onCheckClickListener(task) },
        enabled = true,
        colors = CheckboxDefaults.colors().copy(
            uncheckedBorderColor = if (significance == Significance.HIGH) Red else Gray,
            checkedBoxColor = Green,
            checkedCheckmarkColor = MaterialTheme.colorScheme.primary,
            checkedBorderColor = Green)
    )
}


@Composable
fun TextDescription(task: ToDoItemEntity) {
    Text(
        text = task.description,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodyMedium.copy(
            textDecoration =
            if (task.achievement) {
                TextDecoration.LineThrough
            } else {
                TextDecoration.None
            }
        )
    )
}
