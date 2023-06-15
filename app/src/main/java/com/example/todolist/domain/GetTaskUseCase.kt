package com.example.todolist.domain

class GetTaskUseCase(private val listRepository: ListRepository) {

    fun getTask(taskId : Int): Task? {
        return listRepository.getTask(taskId)
    }
}