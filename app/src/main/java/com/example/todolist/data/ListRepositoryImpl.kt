package com.example.todolist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.domain.list.Task.Companion.UNDEFINED_ID
import com.example.todolist.domain.list.ListRepository
import com.example.todolist.domain.list.Task

object ListRepositoryImpl : ListRepository {

    private var tasksLD = MutableLiveData<List<Task>>()
    private var tasks = mutableListOf<Task>()
    private var counterId = 0
    override fun getList(): LiveData<List<Task>> {
        return tasksLD
    }
    override fun addTask(task: Task) {
        Log.d("Log_App", "\tListRepositoryImpl : addTask called with this item:\n\t $task")
        if (task.id == UNDEFINED_ID){
            task.id = counterId
            counterId++
        }
        tasks.add(task)
        updateList()
    }

    override fun deleteTask(task: Task) {
        Log.d("Log_App", "\tListRepositoryImpl : deleteTask called with this item:\n" +
                "\t $task")
        tasks.remove(task)
        updateList()
    }

    override fun deleteAllTasks() {
        Log.d("Log_App", "\tListRepositoryImpl : deleteAllTasks called ")
        tasks.clear()
        updateList()
    }

    override fun editTask(task: Task) {
        Log.d("Log_App", "\tListRepositoryImpl : editTask called")
        val taskOld = getTaskById(task.id)
        deleteTask(taskOld)
        addTask(task)
    }

    override fun getTaskById(TaskId: Int): Task {
        Log.d("Log_App", "\tListRepositoryImpl : getTaskById called")
        for( item in tasks){
            if (item.id == TaskId){
                return item
            }
        }
        throw RuntimeException("Element with id $TaskId not found")
    }
    private fun updateList() {
        val lengthComparator = Comparator { task: Task, task2: Task -> task.id - task2.id }
        tasksLD.value = tasks.sortedWith(lengthComparator).toList()
    }
    override fun getCountList() : Int{
        return tasks.size
    }
    override fun setCountId(countNew : Int){
        counterId = countNew
    }

    override fun setList(listNew: List<Task>) {
        tasks = listNew.toMutableList()
    }

}