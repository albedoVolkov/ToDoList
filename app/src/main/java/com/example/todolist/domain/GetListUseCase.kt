package com.example.todolist.domain

import com.example.todolist.data.Task

class GetListUseCase {
    private var tasks = ArrayList<Task>()

    fun getList() : MutableList<Task>{
        return tasks
    }
}