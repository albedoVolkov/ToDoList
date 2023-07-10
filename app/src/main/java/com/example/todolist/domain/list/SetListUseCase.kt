package com.example.todolist.domain.list

class SetListUseCase(private val listRepository : ListRepository) {
        fun execute(listNew : List<Task>){
            listRepository.setList(listNew.toList())
        }
    }