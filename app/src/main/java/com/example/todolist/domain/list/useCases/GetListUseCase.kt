package com.example.todolist.domain.list.useCases

import androidx.lifecycle.LiveData
import com.example.todolist.domain.helpers.Task
import com.example.todolist.domain.list.ListRepository

class GetListUseCase(private val listRepository: ListRepository) {
    fun execute() : LiveData<List<Task>> {
        return listRepository.getList()
    }
}