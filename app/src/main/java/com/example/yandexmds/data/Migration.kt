package com.example.yandexmds.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE ToDoItemDBO ADD COLUMN created TEXT NOT NULL DEFAULT ''")
        db.execSQL("ALTER TABLE ToDoItemDBO ADD COLUMN edited TEXT NOT NULL DEFAULT ''")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE ToDoItemDBO_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                description TEXT NOT NULL,
                significance TEXT NOT NULL,
                achievement INTEGER NOT NULL,
                created INTEGER NOT NULL,
                edited INTEGER NOT NULL DEFAULT 0,
                deadline TEXT
            )
        """.trimIndent())

        db.execSQL("""
            INSERT INTO ToDoItemDBO_new (id, description, significance, achievement, created, edited, deadline)
            SELECT id, description, significance, achievement, created, 
                   COALESCE(edited, 0) AS edited,
                   deadline
            FROM ToDoItemDBO
        """.trimIndent())

        db.execSQL("DROP TABLE ToDoItemDBO")

        db.execSQL("ALTER TABLE ToDoItemDBO_new RENAME TO ToDoItemDBO")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            ALTER TABLE ToDoItemDBO 
            RENAME TO ToDoItemDBO_temp;
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS ToDoItemDBO (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                description TEXT NOT NULL, 
                significance TEXT NOT NULL, 
                achievement INTEGER NOT NULL, 
                created INTEGER NOT NULL, 
                edited INTEGER, 
                deadline TEXT
            );
        """.trimIndent())

        db.execSQL("""
            INSERT INTO ToDoItemDBO (id, description, significance, achievement, created, edited, deadline)
            SELECT id, description, significance, achievement, created, edited, deadline
            FROM ToDoItemDBO_temp;
        """.trimIndent())

        db.execSQL("DROP TABLE ToDoItemDBO_temp;")
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE ScheduleItemDBO (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                subject TEXT NOT NULL,
                dayOfWeek TEXT NOT NULL,
                startTime TEXT NOT NULL,
                endTime TEXT NOT NULL,
                teacher TEXT NOT NULL,
                room TEXT NOT NULL,
                color INTEGER NOT NULL
            )
        """)
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE ToDoItemDBO ADD COLUMN scheduleId INTEGER")
    }
}

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Создаем новую временную таблицу с измененной структурой
        db.execSQL("""
            CREATE TABLE ScheduleItemDBO_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                subject TEXT NOT NULL,
                dayOfWeek TEXT NOT NULL,
                startTime TEXT NOT NULL,
                endTime TEXT NOT NULL,
                teacher TEXT NOT NULL,
                room TEXT NOT NULL,
                color INTEGER NOT NULL
            )
        """)

        // Копируем данные из старой таблицы в новую
        db.execSQL("""
            INSERT INTO ScheduleItemDBO_new (id, subject, dayOfWeek, startTime, endTime, teacher, room, color)
            SELECT id, subject, dayOfWeek, startTime, endTime, teacher, room, color FROM ScheduleItemDBO
        """)

        // Удаляем старую таблицу
        db.execSQL("DROP TABLE ScheduleItemDBO")

        // Переименовываем новую таблицу
        db.execSQL("ALTER TABLE ScheduleItemDBO_new RENAME TO ScheduleItemDBO")
    }
}

val MIGRATION_7_8 = object : Migration(7, 8) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Добавляем временную таблицу с новой структурой
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS ScheduleItemDBO_temp (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                subject TEXT NOT NULL,
                dayOfWeek TEXT NOT NULL,
                startTime TEXT NOT NULL,
                endTime TEXT NOT NULL,
                teacher TEXT NOT NULL,
                room TEXT NOT NULL,
                color INTEGER
            )
            """
        )

        // Переносим данные из старой таблицы
        db.execSQL(
            """
            INSERT INTO ScheduleItemDBO_temp (id, subject, dayOfWeek, startTime, endTime, teacher, room, color)
            SELECT id, subject, dayOfWeek, startTime, endTime, teacher, room, color
            FROM ScheduleItemDBO
            """
        )

        // Удаляем старую таблицу
        db.execSQL("DROP TABLE ScheduleItemDBO")

        // Переименовываем новую таблицу в оригинальное название
        db.execSQL("ALTER TABLE ScheduleItemDBO_temp RENAME TO ScheduleItemDBO")
    }
}

val MIGRATION_8_9 = object : Migration(8, 9) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Создаем новую временную таблицу с обновленной структурой
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS ScheduleItemDBO_temp (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                subject TEXT NOT NULL,
                dayOfWeek TEXT NOT NULL,
                startTime TEXT NOT NULL,
                endTime TEXT NOT NULL,
                teacher TEXT,
                room TEXT,
                color INTEGER,
                scheduleType TEXT
            )
            """
        )

        // Переносим данные из старой таблицы в новую, добавляя значения NULL для новых столбцов
        db.execSQL(
            """
            INSERT INTO ScheduleItemDBO_temp (id, subject, dayOfWeek, startTime, endTime, teacher, room, color)
            SELECT id, subject, dayOfWeek, startTime, endTime, teacher, room, color
            FROM ScheduleItemDBO
            """
        )

        // Удаляем старую таблицу
        db.execSQL("DROP TABLE ScheduleItemDBO")

        // Переименовываем временную таблицу в оригинальное название
        db.execSQL("ALTER TABLE ScheduleItemDBO_temp RENAME TO ScheduleItemDBO")
    }
}



