package com.example.todolist.domain

import com.example.todolist.data.Task

class GetTaskUseCase {

    fun getTask(TaskId : Int): Task? {
        val list = GetListUseCase()
        for( item in list.getList()){
            if (item.id == TaskId){
                return item
            }
        }
        return null
    }
}