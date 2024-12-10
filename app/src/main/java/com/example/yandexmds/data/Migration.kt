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



