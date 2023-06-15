package com.example.todolist.domain

class EditTaskUseCase(private val listRepository: ListRepository)  {

    fun editTask(task: Task) {
        listRepository.editTask(task)
    }
}