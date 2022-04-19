package com.pooja.projectsapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pooja.projectsapp.room.entity.ProjectEntity


@Dao
interface ProjectDao {
    @Query("SELECT * FROM project_table ORDER BY position DESC")
    fun getAllProjects(): LiveData<List<ProjectEntity>>

    @Query("SELECT name FROM project_table WHERE position = :position")
    fun getProject(position: Int): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProject(tabEntity: ProjectEntity)

    @Query("DELETE FROM project_table WHERE position = :position")
    suspend fun deleteProject(position: Int)

    @Query("DELETE FROM project_table WHERE name = :name and position = :position")
    suspend fun deleteProject(name: String, position: Int)

    @Delete
    fun deleteAllProjects(tabEntity: ProjectEntity)

    @Query("DELETE FROM project_table")
    fun deleteAllProjects()

    @Query("UPDATE project_table SET name = :name, starDate = :starDate, startTime = :startTime, endDate= :endDate, endTime= :endTime WHERE position = :position")
    suspend fun updateProject(position: Int, name: String, starDate: String, startTime: String, endDate: String, endTime: String)

    /*@Query("SELECT COUNT(*) FROM project_table")
    fun getProjectCount()*/

}