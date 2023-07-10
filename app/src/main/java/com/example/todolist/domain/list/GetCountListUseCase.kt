package com.example.todolist.domain.list

class GetCountListUseCase(private val listRepository: ListRepository) {
        fun execute() : Int {
            return listRepository.getCountList()
        }
}