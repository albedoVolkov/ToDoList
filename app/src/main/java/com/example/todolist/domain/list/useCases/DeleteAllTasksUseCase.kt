package com.example.todolist.domain.list.useCases

import com.example.todolist.domain.list.ListRepository

class DeleteAllTasksUseCase(private val listRepository: ListRepository) {
    fun execute(){
        listRepository.deleteAllTasks()
    }
}