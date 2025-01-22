package com.example.yandexmds.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val unselectedIcons =
        listOf(Icons.AutoMirrored.Outlined.List, Icons.Outlined.Schedule)
    val selectedIcons =
        listOf(Icons.AutoMirrored.Filled.List, Icons.Filled.Schedule)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // 0 - если пользователь на экране, связанном с задачами, 1 - если на экране, связанном с расписанием
    val currentRoute =
        when {
            isRouteInGroup(navBackStackEntry?.destination?.route, Screen.TasksMain.route) -> 0
            isRouteInGroup(navBackStackEntry?.destination?.route, Screen.TaskAdd.route) -> 0
            isRouteInGroup(navBackStackEntry?.destination?.route, Screen.TaskEdit.route) -> 0
            isRouteInGroup(navBackStackEntry?.destination?.route, Screen.ScheduleMain.route) -> 1
            isRouteInGroup(navBackStackEntry?.destination?.route, Screen.ScheduleAdd.route) -> 1
            isRouteInGroup(navBackStackEntry?.destination?.route, Screen.ScheduleEdit.route) -> 1
            else -> 0
        }
    NavigationBar {
        NavBarItems.items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if(currentRoute == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item.name
                    )
                },
                label = { Text(item.name) },
                selected = currentRoute == index,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(item.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

fun isRouteInGroup(route: String?, groupRoute: String): Boolean {
    return route?.startsWith(groupRoute) == true
}