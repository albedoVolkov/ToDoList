package com.example.todolist.data

import android.util.Log
import com.example.todolist.domain.Task.Companion.UNDEFINED_ID
import com.example.todolist.domain.ListRepository
import com.example.todolist.domain.Task

object ListRepositoryImpl : ListRepository {

    private var tasks = mutableListOf<Task>()
    private var counterId = 0

    override fun getList(): List<Task> {
        return tasks.toList()
    }

    override fun addTask(task: Task) {
        Log.d("Log_App", "ListRepositoryImpl : addTask called")
        if (task.id == UNDEFINED_ID){
            task.id = counterId
            tasks.add(task)
            Log.d("Log_App", "ListRepositoryImpl : addTask is successful 1")
        }else{
            tasks.add(task)
            Log.d("Log_App", "ListRepositoryImpl : addTask is successful 2")
        }
        counterId++
    }

    override fun deleteTask(task: Task) {
        tasks.remove(task)
    }

    override fun editTask(task: Task) {
        val taskOld = getTask(task.id)
        tasks.remove(taskOld)
        tasks.add(task)
    }

    override fun getTask(TaskId: Int): Task? {
        for( item in tasks){
            if (item.id == TaskId){
                return item
            }
        }
        return null
    }

}