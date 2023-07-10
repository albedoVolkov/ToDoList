package com.example.todolist.domain.list
class GetTaskByIdUseCase(private val listRepository: ListRepository) {
    fun execute(Id : Int): Task {
        return listRepository.getTaskById(Id)
    }
}