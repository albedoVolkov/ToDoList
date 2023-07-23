package com.example.todolist.domain.list.useCases

import com.example.todolist.domain.helpers.Task
import com.example.todolist.domain.list.ListRepository

class SetListUseCase(private val listRepository : ListRepository) {
        fun execute(listNew : List<Task>){
            listRepository.setList(listNew.toList())
        }
    }