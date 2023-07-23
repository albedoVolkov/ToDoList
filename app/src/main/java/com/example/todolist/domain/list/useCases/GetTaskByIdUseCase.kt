package com.example.todolist.domain.list.useCases

import com.example.todolist.domain.helpers.Task
import com.example.todolist.domain.list.ListRepository

class GetTaskByIdUseCase(private val listRepository: ListRepository) {
    fun execute(Id : Long): Task {
        return listRepository.getTaskById(Id)
    }
}