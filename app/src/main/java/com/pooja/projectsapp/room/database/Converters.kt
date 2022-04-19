package com.pooja.projectsapp.room.database

import androidx.room.TypeConverter
import java.util.*

object Converters {

    @TypeConverter
    fun toDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toLong(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}
