package com.example.yandexmds.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(
    mainScreen: @Composable (navController: NavController) -> Unit,
    addScreen: @Composable () -> Unit,
    editScreen: @Composable (id: Int) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ROUTE_MAIN) {
        composable(Screen.Main.route) {
            mainScreen(navController)
        }
        composable(Screen.Add.route) {
            addScreen()
        }
        composable(
            route = Screen.Edit.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            editScreen(navBackStackEntry.arguments?.getInt("id") ?: 0)
        }
    }
}