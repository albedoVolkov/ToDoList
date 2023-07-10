package com.example.todolist.domain.list

class SetCountIdUseCase(private val listRepository : ListRepository) {
    fun execute(countNew : Int){
        listRepository.setCountId(countNew)
    }
}