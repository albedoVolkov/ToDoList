package com.example.todolist.presentation

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.CacheRepositoryImpl
import com.example.todolist.data.ListRepositoryImpl
import com.example.todolist.domain.cache.GetDataUseCase
import com.example.todolist.domain.cache.SaveDataUseCase
import com.example.todolist.domain.list.AddTaskUseCase
import com.example.todolist.domain.list.DeleteAllTasksUseCase
import com.example.todolist.domain.list.DeleteTaskUseCase
import com.example.todolist.domain.list.EditTaskUseCase
import com.example.todolist.domain.list.GetListUseCase
import com.example.todolist.domain.list.GetTaskByIdUseCase
import com.example.todolist.domain.list.Status
import com.example.todolist.domain.list.Task

class MainViewModel : ViewModel() {
//
//    fun edit(task : Task) {
//        EditTaskUseCase(listRepository = ListRepositoryImpl).execute(task)
//    }

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

    fun saveData(list : List<Task> , sharedPref : SharedPreferences) {
        SaveDataUseCase(CacheRepositoryImpl).execute(list, sharedPref)
    }

    fun getData(sharedPref : SharedPreferences){
        GetDataUseCase(CacheRepositoryImpl).execute(sharedPref)
    }

    fun getTaskById(id : Int) : Task {
        return GetTaskByIdUseCase(listRepository = ListRepositoryImpl).execute(id)
    }


}