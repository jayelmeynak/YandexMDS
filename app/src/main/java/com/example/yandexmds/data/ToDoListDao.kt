package com.example.yandexmds.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoListDao {

    @Insert
    suspend fun addToDo(item: ToDoItemDBO)

    @Update
    suspend fun editToDo(item: ToDoItemDBO)

    @Delete
    suspend fun deleteToDo(item: ToDoItemDBO)

    @Query("SELECT * FROM ToDoItemDBO WHERE id = :id")
    suspend fun getToDoItem(id: Int): ToDoItemDBO

    @Query("SELECT * FROM ToDoItemDBO")
    fun getToDoList(): LiveData<List<ToDoItemDBO>>
}