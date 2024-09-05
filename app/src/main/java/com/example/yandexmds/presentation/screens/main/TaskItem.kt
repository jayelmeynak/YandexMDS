package com.example.yandexmds.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun TaskItem(
    task: ToDoItemEntity,
    checked: Boolean,
    onCheckClickListener: (ToDoItemEntity) -> Unit,
    onTaskClickListener: (ToDoItemEntity) -> Unit
) {

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(vertical = 12.dp)
            .clickable {
                onTaskClickListener(task)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Check(
            task = task,
            checked = checked,
            onCheckClickListener = onCheckClickListener
        )

        if (task.significance == Significance.USUAL) {
            Column(modifier = Modifier.weight(2F)) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .padding(start = 12.dp),
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
                if (task.deadline != null) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .padding(start = 12.dp),
                        text = task.deadline,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }

        if (task.significance == Significance.HIGH) {
            Column(modifier = Modifier.weight(2F)) {
                Row(
                    modifier = Modifier
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
                if (task.deadline != null) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .padding(start = 12.dp),
                        text = task.deadline,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }
        if (task.significance == Significance.LOW) {
            Column(modifier = Modifier.weight(2F)) {
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp)
                ) {
                    if (!task.achievement) {
                        Icon(
                            modifier = Modifier
                                .size(18.dp),
                            imageVector = Icons.Filled.ArrowDownward, contentDescription = null,
                            tint = Gray
                        )
                    }
                    TextDescription(task)
                }
                if (task.deadline != null) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .padding(start = 12.dp),
                        text = task.deadline,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
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
    checked: Boolean,
    onCheckClickListener: (ToDoItemEntity) -> Unit
) {
    val significance = task.significance
    Checkbox(
        modifier = Modifier
            .size(24.dp)
            .padding(3.dp),
        checked = checked,
        onCheckedChange = {
            onCheckClickListener(task)
        },
        enabled = true,
        colors = CheckboxDefaults.colors().copy(
            uncheckedBorderColor = if (significance == Significance.HIGH) Red else Gray,
            checkedBoxColor = Green,
            checkedCheckmarkColor = MaterialTheme.colorScheme.primary,
            checkedBorderColor = Green
        )
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
