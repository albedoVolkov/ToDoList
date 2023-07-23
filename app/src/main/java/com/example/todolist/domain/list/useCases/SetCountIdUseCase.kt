package com.example.todolist.domain.list.useCases

import com.example.todolist.domain.list.ListRepository

class SetCountIdUseCase(private val listRepository : ListRepository) {
    fun execute(countNew : Long){
        listRepository.setCountId(countNew)
    }
}