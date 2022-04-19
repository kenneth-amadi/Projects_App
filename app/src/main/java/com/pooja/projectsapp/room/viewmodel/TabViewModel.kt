package com.pooja.projectsapp.room.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.pooja.projectsapp.room.database.AppDatabase
import com.pooja.projectsapp.room.entity.ProjectEntity
import com.pooja.projectsapp.room.repository.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    private val mAction: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun getVM(): LiveData<Boolean> {
        return mAction
    }

    private var repository: ProjectRepository
    val getAllProjects: LiveData<List<ProjectEntity>>

    init {
        val tabDao = AppDatabase.getDatabase(application).tabDao()
        repository = ProjectRepository(tabDao)
        getAllProjects = repository.getAllProjects
    }

    fun getProject(position: Int): LiveData<List<String>> {
        return repository.getProject(position)
    }

    fun addProject(projectEntity: ProjectEntity) = viewModelScope.launch(Dispatchers.IO) { repository.addProject(projectEntity) }

    fun deleteProject(position: Int) = viewModelScope.launch(Dispatchers.IO) { repository.deleteProject(position) }

    fun deleteProject(url: String, position: Int) = viewModelScope.launch(Dispatchers.IO) { repository.deleteProject(url, position) }

    fun deleteAllProjects(projectEntity: ProjectEntity) {
        repository.deleteAllProjects(projectEntity)
    }

    fun deleteAllProjects() {
        repository.deleteAllProjects()
    }

    fun updateProject(position: Int, name: String, startDate: String, startTime: String, endDate: String, endTime: String) =
        viewModelScope.launch(Dispatchers.IO) { repository.updateProject(position, name, startDate, startTime, endDate, endTime) }

}