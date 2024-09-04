package com.example.yandexmds.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yandexmds.presentation.screens.addAndEdit.AddScreen
import com.example.yandexmds.presentation.screens.main.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(Screen.Add.route) {
            AddScreen(id = null, navController = navController)
        }
        composable(
            route = Screen.Edit.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("id")
            if (id != null) {
                AddScreen(id = id, navController = navController)
            }
        }
    }
}
