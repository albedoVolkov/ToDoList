package com.example.todolist.data


data class Task(
    val id: Int,
    var title: String = "",
    var description: String = "",
    var enabled: Boolean = true,
    var importance: Int
    ){
    override fun toString(): String {
        return "Task [id: ${this.id}," +
                " title: ${this.title}," +
                " description: ${this.description}," +
                " enabled: ${this.enabled}," +
                " importance: ${this.importance}]"
    }
}