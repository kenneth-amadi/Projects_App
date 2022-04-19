package com.pooja.projectsapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pooja.projectsapp.room.dao.ProjectDao


import com.pooja.projectsapp.room.entity.ProjectEntity


@Database(entities = [ProjectEntity::class], version = 1, exportSchema = true)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tabDao(): ProjectDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "app_database"
                )
                    .addMigrations(object : Migration(1, 2) {
                        override fun migrate(database: SupportSQLiteDatabase) {
                            database.execSQL("ALTER TABLE codes ADD COLUMN name TEXT")
                        }
                    })
                    .build()
                INSTANCE = instance

                instance
            }
        }

    }
}