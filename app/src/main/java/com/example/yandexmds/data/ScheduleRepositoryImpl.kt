package com.example.yandexmds.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.yandexmds.domain.model.ScheduleItemEntity
import com.example.yandexmds.domain.repository.ScheduleRepository

class ScheduleRepositoryImpl(application: Application) : ScheduleRepository {
    private val scheduleDao = DataBaseToDo.getInstance(application).scheduleDao()
    val mapper = Mapper()

    override suspend fun addScheduleItem(item: ScheduleItemEntity) {
        scheduleDao.insertScheduleItem(mapper.mapEntityToDBModel(item))
    }

    override suspend fun deleteScheduleItem(item: ScheduleItemEntity) {
        scheduleDao.deleteScheduleItem(mapper.mapEntityToDBModel(item))
    }

    override suspend fun editScheduleItem(item: ScheduleItemEntity) {
        scheduleDao.updateScheduleItem(mapper.mapEntityToDBModel(item))
    }

    override suspend fun getScheduleItem(id: Int): ScheduleItemEntity {
        return mapper.mapDBModelToEntity(scheduleDao.getScheduleItem(id))
    }

    override fun getScheduleList(): LiveData<List<ScheduleItemEntity>> {
        return scheduleDao.getAllScheduleItems().map { list ->
            list.map { item ->
                mapper.mapDBModelToEntity(item)
            }
        }
    }
}