package com.example.todolist.domain

import android.util.Log

class AddTaskUseCase(private val listRepository: ListRepository) {

    fun addTask(task: Task) {
        Log.d("Log_App", "AddTaskUseCase : addTask  called")
        listRepository.addTask(task)
        Log.d("Log_App", "AddTaskUseCase : addTask  is successful")
    }
}