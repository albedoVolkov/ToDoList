package com.example.todolist.domain.list.useCases

import com.example.todolist.domain.helpers.Task
import com.example.todolist.domain.list.ListRepository

class DeleteTaskUseCase(private val listRepository: ListRepository) {

    fun execute(task: Task) {
        listRepository.deleteTask(task)
    }
}