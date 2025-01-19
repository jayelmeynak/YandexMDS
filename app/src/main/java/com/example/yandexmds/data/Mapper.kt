package com.example.yandexmds.data

import com.example.yandexmds.domain.model.ScheduleItemEntity
import com.example.yandexmds.domain.model.ToDoItemEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Mapper {

    fun mapDBModelToEntity(item: ToDoItemDBO): ToDoItemEntity {
        return ToDoItemEntity(
            id = item.id,
            description = item.description,
            significance = item.significance,
            achievement = item.achievement,
            created = mapTimeMillisToLocalDateTime(item.created),
            edited = if (item.edited != null) mapTimeMillisToLocalDateTime(item.edited) else null,
            deadline = item.deadline,
            scheduleId = item.scheduleId
        )
    }

    fun mapDBModelToEntity(item: ScheduleItemDBO): ScheduleItemEntity {
        return ScheduleItemEntity(
            id = item.id,
            subject = item.subject,
            dayOfWeek = item.dayOfWeek,
            startTime = item.startTime,
            endTime = item.endTime,
            teacher = item.teacher,
            room = item.room,
            color = item.color,
            scheduleType = item.scheduleType
        )
    }

    fun mapEntityToDBModel(item: ToDoItemEntity): ToDoItemDBO {
        return ToDoItemDBO(
            id = item.id,
            description = item.description,
            significance = item.significance,
            achievement = item.achievement,
            created = mapLocalDateTimeToTimesMillis(item.created),
            edited = if (item.edited != null) mapLocalDateTimeToTimesMillis(item.edited) else null,
            deadline = item.deadline,
            scheduleId = item.scheduleId
        )
    }

    fun mapEntityToDBModel(item: ScheduleItemEntity): ScheduleItemDBO {
        return ScheduleItemDBO(
            id = item.id,
            subject = item.subject,
            dayOfWeek = item.dayOfWeek,
            startTime = item.startTime,
            endTime = item.endTime,
            teacher = item.teacher,
            room = item.room,
            color = item.color,
            scheduleType = item.scheduleType
        )
    }

    fun mapTimeMillisToLocalDateTime(millis: Long): String {
        val localDateTime =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
        val formattedDate = localDateTime.format(formatter)
        return formattedDate
    }

    fun mapLocalDateTimeToTimesMillis(formattedDate: String): Long {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
        val parsedDateTime = LocalDateTime.parse(formattedDate, formatter)
        val millis = parsedDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return millis
    }
}