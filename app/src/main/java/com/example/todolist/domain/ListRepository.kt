package com.example.todolist.domain

interface ListRepository {

    fun getList() : List<Task>

    fun addTask(task: Task)

    fun deleteTask(task: Task)

    fun editTask(task: Task)

    fun getTask(TaskId : Int): Task?

}