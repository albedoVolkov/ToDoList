package com.example.todolist.domain.list

class DeleteAllTasksUseCase(private val listRepository: ListRepository) {
    fun execute(){
        listRepository.deleteAllTasks()
    }
}