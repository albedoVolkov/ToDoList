package com.example.todolist.domain

import com.example.todolist.data.Task

class GetListUseCase {
    private var tasks = ArrayList<Task>()

    fun getShopList() : MutableList<Task>{
        return tasks
    }
}