package com.example.todolist.domain.list
class EditTaskUseCase(private val listRepository: ListRepository)  {

    fun execute(task: Task) {
        listRepository.editTask(task)
    }
}