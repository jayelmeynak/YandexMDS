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

