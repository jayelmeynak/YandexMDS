package com.example.yandexmds.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.yandexmds.presentation.screens.scheduleScreens.AddEditScheduleScreen
import com.example.yandexmds.presentation.screens.scheduleScreens.ScheduleMainScreen
import com.example.yandexmds.presentation.screens.taskScreens.AddEditTaskScreen
import com.example.yandexmds.presentation.screens.taskScreens.main.TaskMainScreen

@Composable
fun Navigation(navController: NavHostController, outerPadding: PaddingValues) {

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
            NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(navController.graph.findStartDestination().id, false, true)
                .build()
            TaskMainScreen(
                navController = navController,
                outerPadding = outerPadding
            )
        }
        composable(Screen.TaskAdd.route) {
            AddEditTaskScreen(
                id = null, navController = navController,
                outerPadding = outerPadding
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
                    outerPadding = outerPadding
                )
            }
        }
        composable(Screen.ROUTE_SCHEDULE_MAIN) {
            ScheduleMainScreen(
                navController = navController,
                outerPadding = outerPadding
            )
        }
        composable(Screen.ROUTE_SCHEDULE_ADD) {
            AddEditScheduleScreen(
                id = null, navController = navController,
                outerPadding = outerPadding
            )
        }
    }
}
