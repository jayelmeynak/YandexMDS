package com.example.yandexmds.domain.model

sealed class Weekday(val name: String) {
    object Monday : Weekday("Понедельник")
    object Tuesday : Weekday("Вторник")
    object Wednesday : Weekday("Среда")
    object Thursday : Weekday("Четверг")
    object Friday : Weekday("Пятница")
    object Saturday : Weekday("Суббота")
    object Sunday : Weekday("Воскресенье")
}