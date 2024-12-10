package com.example.yandexmds.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yandexmds.presentation.screens.addAndEdit.AddEditTaskScreen
import com.example.yandexmds.presentation.screens.main.TaskMainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val items = listOf("Задачи", "Расписание")
    val selectedItem = remember { mutableStateOf(0) }
    val unselectedIcons = listOf(Icons.AutoMirrored.Outlined.List, Icons.Outlined.Schedule)
    val selectedIcons = listOf(Icons.AutoMirrored.Filled.List, Icons.Filled.Schedule)

    NavHost(
        navController = navController,
        startDestination = Screen.TasksMain.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(500)
            )
        }
    ) {
        composable(Screen.TasksMain.route) {
            TaskMainScreen(
                navController = navController,
                selectedItem = selectedItem,
                navigationItems = items,
                unselectedNavigationIcons = unselectedIcons,
                selectedNavigationIcons = selectedIcons
            )
        }
        composable(Screen.TaskAdd.route) {
            AddEditTaskScreen(
                id = null, navController = navController,
                selectedItem = selectedItem,
                navigationItems = items,
                unselectedNavigationIcons = unselectedIcons,
                selectedNavigationIcons = selectedIcons
            )
        }
        composable(
            route = Screen.TaskEdit.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("id")
            if (id != null) {
                AddEditTaskScreen(
                    id = id,
                    navController = navController,
                    selectedItem = selectedItem,
                    navigationItems = items,
                    unselectedNavigationIcons = unselectedIcons,
                    selectedNavigationIcons = selectedIcons
                )
            }
        }
        composable(Screen.ROUTE_SCHEDULE_MAIN) {
            AddEditTaskScreen(
                id = null,
                navController = navController,
                selectedItem = selectedItem,
                navigationItems = items,
                unselectedNavigationIcons = unselectedIcons,
                selectedNavigationIcons = selectedIcons
            )
        }
    }
}
