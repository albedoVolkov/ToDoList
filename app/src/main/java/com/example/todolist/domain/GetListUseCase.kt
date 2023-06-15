package com.example.todolist.domain

class GetListUseCase(private val listRepository: ListRepository) {
    fun getList() : List<Task>{
        return listRepository.getList()
    }
}