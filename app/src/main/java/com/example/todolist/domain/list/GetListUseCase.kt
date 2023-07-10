package com.example.todolist.domain.list

import androidx.lifecycle.LiveData

class GetListUseCase(private val listRepository: ListRepository) {
    fun execute() : LiveData<List<Task>> {
        return listRepository.getList()
    }
}