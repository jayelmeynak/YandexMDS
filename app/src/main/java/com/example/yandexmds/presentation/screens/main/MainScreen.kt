package com.example.yandexmds.presentation.screens.main

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexmds.R
import com.example.yandexmds.presentation.navigation.Screen
import com.example.yandexmds.ui.theme.Blue
import com.example.yandexmds.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)
    val isFilter by viewModel.isFilter.observeAsState(false)
    val taskList =
        (if (isFilter) viewModel.filterTasksList else viewModel.allTasksList).observeAsState(
            listOf()
        )
    val addScreenOpening = remember { mutableStateOf(false) }
    val countCompletedTasks by viewModel.countCompletedTask
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                scrollBehavior,
                countCompletedTasks,
                isFilter,
                onVisibilityClick = {
                    viewModel.changeFilter()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                containerColor = Blue,
                onClick = {
                    if (!addScreenOpening.value) {
                        addScreenOpening.value = true
                        navController.navigate(Screen.Add.route)
                    }
                }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = null,
                    tint = White
                )
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .padding(8.dp),
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(10.dp)
        ) {
            if (taskList.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Список задач пуст",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    items(taskList.value, key = { it.id }) { model ->
                        var checked by remember { mutableStateOf(model.achievement) }
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = {
                                when (it) {
                                    SwipeToDismissBoxValue.StartToEnd -> {
                                        viewModel.changeAchievement(model)
                                        checked = !checked
                                        return@rememberSwipeToDismissBoxState false
                                    }

                                    SwipeToDismissBoxValue.EndToStart -> {
                                        viewModel.deleteTask(model)
                                        return@rememberSwipeToDismissBoxState false
                                    }

                                    SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
                                }
                            }
                        )
                        LaunchedEffect(dismissState.currentValue) {
                            when (dismissState.currentValue) {
                                SwipeToDismissBoxValue.StartToEnd -> {
                                    viewModel.changeAchievement(model)
                                    checked = !checked
                                }

                                SwipeToDismissBoxValue.EndToStart -> {
                                    viewModel.deleteTask(model)
                                }

                                else -> {
                                }
                            }
                        }
                        SwipeToDismissBox(
                            modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                            state = dismissState,
                            backgroundContent = {
                                DismissBackground(
                                    dismissState = dismissState
                                )
                            }) {
                            TaskItem(
                                task = model,
                                checked = checked,
                                onCheckClickListener = { task ->
                                    viewModel.changeAchievement(task)
                                    checked = !checked
                                },
                                onTaskClickListener = { task ->
                                    if (!addScreenOpening.value) {
                                        addScreenOpening.value = true
                                        val id = task.id
                                        navController.navigate(Screen.Edit.route + "/$id")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
