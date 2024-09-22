package com.example.yandexmds.data

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
            edited = mapTimeMillisToLocalDateTime(item.edited),
            deadline = item.deadline
        )
    }

    fun mapEntityToDBModel(item: ToDoItemEntity): ToDoItemDBO {
        return ToDoItemDBO(
            id = item.id,
            description = item.description,
            significance = item.significance,
            achievement = item.achievement,
            created = mapLocalDateTimeToTimesMillis(item.created),
            edited = mapLocalDateTimeToTimesMillis(item.edited),
            deadline = item.deadline
        )
    }

    fun mapTimeMillisToLocalDateTime(millis: Long): String {
        val localDateTime =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
        val formattedDate = localDateTime.format(formatter)
        return formattedDate
    }

    fun mapLocalDateTimeToTimesMillis(formattedDate: String): Long{
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
        val parsedDateTime = LocalDateTime.parse(formattedDate, formatter)
        val millis = parsedDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return millis
    }
}