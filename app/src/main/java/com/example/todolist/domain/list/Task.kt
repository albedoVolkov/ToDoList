package com.example.todolist.domain.list


data class Task(
    var title: String,
    var description: String,
    var status: Status = Status.NotDone,
    var id: Int = UNDEFINED_ID
    ){
    companion object{
       const val UNDEFINED_ID = -1
    }
    override fun toString(): String {
        return "Task [id: ${this.id}," +
                " title: ${this.title}," +
                " description: ${this.description}," +
                " status: ${this.status}]"
    }
}