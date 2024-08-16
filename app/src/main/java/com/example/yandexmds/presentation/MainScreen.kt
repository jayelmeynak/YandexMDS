package com.example.yandexmds.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yandexmds.R
import com.example.yandexmds.domain.model.Significance
import com.example.yandexmds.domain.model.ToDoItemEntity
import com.example.yandexmds.ui.theme.Blue
import com.example.yandexmds.ui.theme.Green
import com.example.yandexmds.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val viewModel: MainViewModel = viewModel()
    val scrollState = rememberLazyListState()
    val derivedScroll = remember {
        derivedStateOf { scrollState.firstVisibleItemScrollOffset }
    }
    val taskList = viewModel.taskList.observeAsState(listOf())
    val maxHeight = 200.dp
    val minHeight = 56.dp
    val countCompletedTasks = remember {
        mutableIntStateOf(0)
    }

    val topBarHeight by animateDpAsState(
        targetValue = if (derivedScroll.value > 0) minHeight else maxHeight
    )

    Scaffold(
        topBar = {
            TopBar(topBarHeight, countCompletedTasks.intValue)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = null,
                    tint = Blue
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp),

            ) {
            items(taskList.value, key = { it.id }) { model ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        when (it) {
                            SwipeToDismissBoxValue.StartToEnd -> {
                                viewModel.changeAchievement(model)
                            }

                            SwipeToDismissBoxValue.EndToStart -> {
                                viewModel.deleteTask(model)
                            }

                            SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
                        }
                        return@rememberSwipeToDismissBoxState true
                    }
                )
                SwipeToDismissBox(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    backgroundContent = {
                        DismissBackground(
                            dismissState = dismissState
                        )
                    }) {
                    TaskItem(model)
                }
            }
        }
    }
}


@Composable
fun TopBar(height: Dp, countCompletedTasks: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(top = 82.dp, start = 60.dp, bottom = 4.dp),
                text = "Мои дела",
                style = MaterialTheme.typography.titleLarge
            )
//            Spacer(modifier = Modifier.height())
            Text(
                modifier = Modifier.padding(start = 60.dp),
                text = "Выполнено - $countCompletedTasks",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Green
        SwipeToDismissBoxValue.EndToStart -> Red
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.check),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier)
        Icon(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun TaskItem(task: ToDoItemEntity) {
    Row(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(3.dp)
                .weight(1F),
            painter = painterResource(
                id = if (task.achievement) {
                    R.drawable.checkbox_checked
                } else {
                    if (task.significance == Significance.HIGH) {
                        R.drawable.checkbox_unchecked_high
                    } else {
                        R.drawable.checkbox_unchecked_normal
                    }
                }
            ),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))

        if (task.significance == Significance.USUAL) {
            Text(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .weight(2F),
                text = task.description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration =
                    if (!task.achievement) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )
            )
        }
        if (task.significance == Significance.HIGH) {
            Row(modifier = Modifier.weight(2F)) {
                Icon(
                    modifier = Modifier
                        .size(10.dp, 16.dp)
                        .padding(vertical = 2.dp, horizontal = 3.dp),
                    painter = painterResource(id = R.drawable.high), contentDescription = null
                )
                Spacer(modifier = Modifier.width(3.dp))
                TextDescription(task)
            }
        }
        if (task.significance == Significance.LOW) {
            Row(modifier = Modifier.weight(2F)) {
                Icon(
                    modifier = Modifier
                        .size(10.dp, 16.dp)
                        .padding(vertical = 3.dp, horizontal = 2.5.dp),
                    painter = painterResource(id = R.drawable.low), contentDescription = null
                )
                Spacer(modifier = Modifier.width(3.dp))
                TextDescription(task)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            painter = painterResource(id = R.drawable.info_outline), contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary
        )

    }
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
            if (!task.achievement) {
                TextDecoration.LineThrough
            } else {
                TextDecoration.None
            }
        )
    )
}