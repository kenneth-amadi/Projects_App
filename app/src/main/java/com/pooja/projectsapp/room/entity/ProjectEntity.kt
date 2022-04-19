package com.pooja.projectsapp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_table")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    var position: Int,
    var name: String,
    var starDate: String,
    var startTime: String,
    var endDate: String,
    var endTime: String,
)