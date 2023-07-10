package com.example.todolist.domain.list

import androidx.lifecycle.LiveData

interface ListRepository {

    fun getList() : LiveData<List<Task>>

    fun addTask(task: Task)

    fun deleteTask(task: Task)

    fun deleteAllTasks()

    fun editTask(task: Task)

    fun getTaskById(TaskId : Int) : Task

    fun getCountList() : Int

    fun setCountId(countNew : Int)

    fun setList(listNew : List<Task>)
}