package com.example.todolist.data


data class Task(
    val id: Int,
    var title: String = "",
    var description: String = "",
    var enabled: Boolean = true,
    var importance: Int
    )