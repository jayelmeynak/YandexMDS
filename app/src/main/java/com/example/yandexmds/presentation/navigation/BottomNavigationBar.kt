package com.example.yandexmds.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController, currentDestination: String) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
            label = { Text("Задачи") },
            selected = currentDestination == "tasks",
            onClick = { navController.navigate(Screen.ROUTE_TASKS_MAIN) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Schedule, contentDescription = null) },
            label = { Text("Расписание") },
            selected = currentDestination == "schedule",
            onClick = { navController.navigate("schedule") }
        )
    }
}