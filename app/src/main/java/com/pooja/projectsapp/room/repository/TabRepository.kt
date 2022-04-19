package com.pooja.projectsapp.room.repository

import androidx.lifecycle.LiveData
import com.pooja.projectsapp.room.dao.ProjectDao
import com.pooja.projectsapp.room.entity.ProjectEntity

class ProjectRepository(private var tabDao: ProjectDao) {

    val getAllProjects: LiveData<List<ProjectEntity>> = tabDao.getAllProjects()

    fun getProject(position: Int): LiveData<List<String>> {
        return tabDao.getProject(position)
    }

    suspend fun addProject(projectEntity: ProjectEntity) {
        tabDao.addProject(projectEntity)
    }

    suspend fun deleteProject(position: Int) {
        tabDao.deleteProject(position)
    }

    suspend fun deleteProject(url: String, position: Int) {
        tabDao.deleteProject(url, position)
    }

    fun deleteAllProjects(projectEntity: ProjectEntity) {
        tabDao.deleteAllProjects(projectEntity)
    }

    fun deleteAllProjects() {
        tabDao.deleteAllProjects()
    }

    suspend fun updateProject(position: Int, name: String, startDate: String, startTime: String, endDate: String, endTime: String) {
        tabDao.updateProject(position, name, startDate, startTime, endDate, endTime)
    }
}