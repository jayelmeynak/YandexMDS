package com.example.yandexmds.presentation.navigation

sealed class Screen(
    val route: String,
    val name: String
) {
    object TasksMain : Screen(ROUTE_TASKS_MAIN, "Задачи")
    object TaskAdd : Screen(ROUTE_TASK_ADD, "Добавить задачу")
    object TaskEdit : Screen(ROUTE_TASK_EDIT, "Редактировать задачу")
    object ScheduleMain : Screen(ROUTE_SCHEDULE_MAIN, "Расписание")
    object ScheduleAdd : Screen(ROUTE_SCHEDULE_ADD, "Добавить занятие")
    object ScheduleEdit : Screen(ROUTE_SCHEDULE_EDIT, "Редактировать занятие")

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