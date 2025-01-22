package com.example.yandexmds.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.yandexmds.domain.model.ScheduleItemEntity
import com.example.yandexmds.domain.repository.ScheduleRepository
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val scheduleDao: ScheduleDao) : ScheduleRepository {
    private val mapper = Mapper()

    private val daysOrder = mapOf(
        "Понедельник" to 1,
        "Вторник" to 2,
        "Среда" to 3,
        "Четверг" to 4,
        "Пятница" to 5,
        "Суббота" to 6,
        "Воскресенье" to 7
    )

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
        return sortScheduleByDayOfWeek(scheduleDao.getAllScheduleItems().map { list ->
            list.map { item ->
                mapper.mapDBModelToEntity(item)
            }
        })
    }

    override suspend fun deleteAllScheduleItems() {
        scheduleDao.deleteAllScheduleItems()
    }

    private fun sortScheduleByDayOfWeek(scheduleList: LiveData<List<ScheduleItemEntity>>): LiveData<List<ScheduleItemEntity>> {
        return scheduleList.map { list ->
            list.sortedBy { daysOrder[it.dayOfWeek.name] ?: Int.MAX_VALUE }
        }
    }
}