package com.example.yandexmds.presentation.navigation

sealed class Screen(
    val route: String
) {
    object TasksMain : Screen(ROUTE_TASKS_MAIN)
    object TaskAdd : Screen(ROUTE_TASK_ADD)
    object TaskEdit : Screen(ROUTE_TASK_EDIT)
    object ScheduleMain : Screen(ROUTE_SCHEDULE_MAIN)
    object ScheduleAdd : Screen(ROUTE_SCHEDULE_ADD)
    object ScheduleEdit : Screen(ROUTE_SCHEDULE_EDIT)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    companion object {
        const val ROUTE_TASKS_MAIN = "tasksMain"
        const val ROUTE_TASK_ADD = "taskAdd"
        const val ROUTE_TASK_EDIT = "taskEdit"
        const val ROUTE_SCHEDULE_MAIN = "scheduleMain"
        const val ROUTE_SCHEDULE_ADD = "scheduleAdd"
        const val ROUTE_SCHEDULE_EDIT = "scheduleEdit"
    }
}