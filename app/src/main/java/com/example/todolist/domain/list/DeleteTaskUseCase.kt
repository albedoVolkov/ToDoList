package com.example.todolist.domain.list

class DeleteTaskUseCase(private val listRepository: ListRepository) {

    fun execute(task: Task) {
        listRepository.deleteTask(task)
    }
}