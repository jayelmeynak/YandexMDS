package com.example.yandexmds.presentation.navigation

sealed class Screen(
    val route: String
) {
    object Main : Screen(ROUTE_MAIN)
    object Add : Screen(ROUTE_ADD)
    object Edit : Screen(ROUTE_EDIT)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    companion object {
        const val ROUTE_MAIN = "main"
        const val ROUTE_ADD = "add"
        const val ROUTE_EDIT = "edit"
    }
}