package com.example.todolist.domain.list.useCases

import com.example.todolist.domain.list.ListRepository

class GetCountListUseCase(private val listRepository: ListRepository) {
        fun execute() : Int {
            return listRepository.getCountList()
        }
}