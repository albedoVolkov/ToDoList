package com.example.todolist.presentation

interface FromCreatingItemFragmentToActivity {
    fun communicate(title: String,description: String)
    fun communicate(title: String,description: String,idOld : Int)
}