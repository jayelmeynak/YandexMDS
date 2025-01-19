package com.example.yandexmds.data

import androidx.room.TypeConverter
import com.example.yandexmds.domain.model.Weekday

class WeekdayConverter {
    @TypeConverter
    fun fromWeekday(weekday: Weekday): String {
        return weekday.name
    }

    @TypeConverter
    fun toWeekday(name: String): Weekday {
        return when (name) {
            Weekday.Monday.name -> Weekday.Monday
            Weekday.Tuesday.name -> Weekday.Tuesday
            Weekday.Wednesday.name -> Weekday.Wednesday
            Weekday.Thursday.name -> Weekday.Thursday
            Weekday.Friday.name -> Weekday.Friday
            Weekday.Saturday.name -> Weekday.Saturday
            Weekday.Sunday.name -> Weekday.Sunday
            else -> throw IllegalArgumentException("Unknown weekday: $name")
        }
    }
}
