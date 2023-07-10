package com.example.todolist.domain.list

class AddTaskUseCase(private val listRepository: ListRepository) {
    fun execute(task: Task) {
        listRepository.addTask(task)
    }
}