package com.example.yandexmds.data

import com.example.yandexmds.domain.model.ToDoItemEntity

class Mapper {

    fun mapDBModelToEntity(item: ToDoItemDBO): ToDoItemEntity{
        return ToDoItemEntity(
            id = item.id,
            description = item.description,
            significance = item.significance,
            achievement = item.achievement,
            deadline = item.deadline
        )
    }

    fun mapEntityToDBModel(item: ToDoItemEntity): ToDoItemDBO{
        return ToDoItemDBO(
            id = item.id,
            description = item.description,
            significance = item.significance,
            achievement = item.achievement,
            deadline = item.deadline
        )
    }
}