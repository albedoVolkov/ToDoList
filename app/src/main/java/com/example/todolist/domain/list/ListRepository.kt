package com.example.todolist.domain.list

import androidx.lifecycle.LiveData
import com.example.todolist.domain.helpers.Task

interface ListRepository {

    fun getList() : LiveData<List<Task>>

    fun addTask(task: Task)

    fun deleteTask(task: Task)

    fun deleteAllTasks()

    fun editTask(task: Task)

    fun getTaskById(TaskId : Long) : Task

    fun getCountList() : Int

    fun setCountId(countNew : Long)

    fun setList(listNew : List<Task>)


}