package com.example.todolist.domain

class DeleteTaskUseCase(private val listRepository: ListRepository) {

    fun deleteTask(task: Task) {
        listRepository.deleteTask(task)
    }
}