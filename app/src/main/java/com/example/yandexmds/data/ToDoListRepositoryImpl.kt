package com.example.yandexmds.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.yandexmds.domain.repository.ToDoListRepository
import com.example.yandexmds.domain.model.ToDoItemEntity

class ToDoListRepositoryImpl(application: Application) : ToDoListRepository {
    private val toDoListDao = DataBaseToDo.getInstance(application).toDoListDao()
    val mapper = Mapper()

    override suspend fun addToDo(item: ToDoItemEntity) {
        toDoListDao.addToDo(mapper.mapEntityToDBModel(item))
    }

    override suspend fun deleteToDo(item: ToDoItemEntity) {
        toDoListDao.deleteToDo(mapper.mapEntityToDBModel(item))
    }

    override suspend fun editToDo(item: ToDoItemEntity) {
        toDoListDao.editToDo(mapper.mapEntityToDBModel(item))
    }

    override suspend fun getToDo(id: Int): ToDoItemEntity {
        return mapper.mapDBModelToEntity(toDoListDao.getToDoItem(id))
    }

    override fun getToDoListFilteredByAchievement(): LiveData<List<ToDoItemEntity>> {
        return toDoListDao.getToDoListFilteredByAchievement().map { list ->
            list.map { item ->
                mapper.mapDBModelToEntity(item)
            }
        }
    }

    override fun getToDoList(): LiveData<List<ToDoItemEntity>> {
        return toDoListDao.getToDoList().map { list ->
            list.map { item ->
                mapper.mapDBModelToEntity(item)
            }
        }
    }
}