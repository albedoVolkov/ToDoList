package com.example.todolist.presentation

interface FromTaskActivityToMainActivity {
    fun communicate(title: String,description: String)
    fun communicate(title: String,description: String,idOld : Long)
    fun communicate()

}