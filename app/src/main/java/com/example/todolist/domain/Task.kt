package com.example.todolist.domain


data class Task(
    var title: String,
    var description: String,
    var enabled: Boolean = true,
    var importance: Int,
    var id: Int = UNDEFINED_ID
    ){
    companion object{
       const val UNDEFINED_ID = -1
    }
    override fun toString(): String {
        return "Task [id: ${this.id}," +
                " title: ${this.title}," +
                " description: ${this.description}," +
                " enabled: ${this.enabled}," +
                " importance: ${this.importance}]"
    }
}