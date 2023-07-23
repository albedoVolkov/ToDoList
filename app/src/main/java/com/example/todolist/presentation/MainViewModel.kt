package com.example.todolist.presentation

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.CacheRepositoryImpl
import com.example.todolist.data.ListRepositoryImpl
import com.example.todolist.domain.cache.useCases.GetDataUseCase
import com.example.todolist.domain.cache.useCases.SaveDataUseCase
import com.example.todolist.domain.list.useCases.AddTaskUseCase
import com.example.todolist.domain.list.useCases.DeleteAllTasksUseCase
import com.example.todolist.domain.list.useCases.DeleteTaskUseCase
import com.example.todolist.domain.list.useCases.EditTaskUseCase
import com.example.todolist.domain.list.useCases.GetListUseCase
import com.example.todolist.domain.list.useCases.GetTaskByIdUseCase
import com.example.todolist.domain.helpers.Status
import com.example.todolist.domain.helpers.Task

class MainViewModel : ViewModel() {

    fun edit(task : Task) {
        EditTaskUseCase(listRepository = ListRepositoryImpl).execute(task)
    }

    fun insert(task : Task) {
        AddTaskUseCase(listRepository = ListRepositoryImpl).execute(task)
    }

    fun delete(task : Task) {
        DeleteTaskUseCase(listRepository = ListRepositoryImpl).execute(task)
    }

    fun changeStatusState(task : Task, statusNew : Status) {
        val newTask = task.copy(status = statusNew)
        EditTaskUseCase(listRepository = ListRepositoryImpl).execute(newTask)
    }

    fun deleteAll() {
        DeleteAllTasksUseCase(listRepository = ListRepositoryImpl).execute()
    }

    fun getList() : LiveData<List<Task>> {
        return GetListUseCase(listRepository = ListRepositoryImpl).execute()
    }

    fun saveData(list : List<Task>, sharedPref : SharedPreferences) {
        SaveDataUseCase(CacheRepositoryImpl).execute(list, sharedPref)
    }

    fun getData(sharedPref : SharedPreferences){
        GetDataUseCase(CacheRepositoryImpl).execute(sharedPref)
    }

    fun getTaskById(id : Long) : Task {
        return GetTaskByIdUseCase(listRepository = ListRepositoryImpl).execute(id)
    }


}