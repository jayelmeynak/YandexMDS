package com.example.yandexmds.presentation.screens.scheduleScreens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandexmds.R
import com.example.yandexmds.domain.model.ScheduleItemEntity
import com.example.yandexmds.presentation.navigation.Screen
import com.example.yandexmds.ui.theme.Blue
import com.example.yandexmds.ui.theme.White

@Composable
fun ScheduleMainScreen(
    navController: NavController,
    outerPadding: PaddingValues
) {
    val viewModel: ScheduleViewModel = viewModel()
    val scheduleList = viewModel.groupedScheduleList.observeAsState(emptyMap()).value
    val addScreenOpening = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.padding(bottom = outerPadding.calculateBottomPadding()),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                containerColor = Blue,
                onClick = {
                    if (!addScreenOpening.value) {
                        addScreenOpening.value = true
                        navController.navigate(Screen.ScheduleAdd.route)
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
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(10.dp)
        ) {
            if (scheduleList.isEmpty()) {
                Log.d("MyLog", scheduleList.toString())
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Расписание пустое",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    viewModel.daysOfWeek.forEach { day ->
                        val itemsForDay = scheduleList[day]
                        if (!itemsForDay.isNullOrEmpty()) {
                            item {
                                Text(
                                    text = day,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        itemsForDay.forEach { item ->
                                            ScheduleItemCard(item)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ScheduleItemCard(scheduleItem: ScheduleItemEntity) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = scheduleItem.startTime,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = scheduleItem.endTime,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .width(1.dp)
                    .background(Color(scheduleItem.color))
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )

            Column(modifier = Modifier
                .weight(3f)
                .align(Alignment.Top)) {
                Text(
                    text = scheduleItem.subject,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                scheduleItem.teacher?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                                overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            if (scheduleItem.room != null) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = scheduleItem.room,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                )
            } else {
                Spacer(
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


