package com.example.todolist.domain.helpers


data class Task(
    var title: String,
    var description: String,
    var status: Status = Status.NotDone,
    var id: Long = UNDEFINED_ID
    ){
    companion object{
       const val UNDEFINED_ID = -1L
    }
    override fun toString(): String {
        return "Task [id: ${this.id}," +
                " title: ${this.title}," +
                " description: ${this.description}," +
                " status: ${this.status}]"
    }
}