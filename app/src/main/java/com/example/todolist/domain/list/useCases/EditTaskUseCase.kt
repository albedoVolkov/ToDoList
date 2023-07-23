package com.example.todolist.domain.list.useCases

import com.example.todolist.domain.helpers.Task
import com.example.todolist.domain.list.ListRepository

class EditTaskUseCase(private val listRepository: ListRepository)  {

    fun execute(task: Task) {
        listRepository.editTask(task)
    }
}