package com.example.todolist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.domain.helpers.Status
import com.example.todolist.domain.helpers.Task.Companion.UNDEFINED_ID
import com.example.todolist.domain.list.ListRepository
import com.example.todolist.domain.helpers.Task

object ListRepositoryImpl : ListRepository {

    private var tasksLD = MutableLiveData<List<Task>>()
    private var tasks = mutableListOf<Task>()
    private var counterId = 0L
    private var hideCompleted = false
    override fun getList(): LiveData<List<Task>> {
            return tasksLD
    }
    override fun addTask(task: Task) {
        Log.d("Log_App", "\t addTask() add \n\t $task")
        if (task.id == UNDEFINED_ID){
            task.id = counterId
            counterId++
        }
        tasks.add(task)
        updateList()
    }

    override fun deleteTask(task: Task) {
        Log.d("Log_App", "\t deleteTask() delete \n\t $task")
        tasks.remove(task)
        updateList()
    }

    override fun deleteAllTasks() {
        tasks.clear()
        updateList()
    }

    override fun editTask(task: Task) {
        val taskOld = getTaskById(task.id)
        deleteTask(taskOld)
        addTask(task)
    }

    override fun getTaskById(TaskId: Long): Task {
        for( item in tasks){
            if (item.id == TaskId){
                return item
            }
        }
        throw RuntimeException("Element with id $TaskId not found")
    }
    private fun updateList() {
        val lengthComparator = Comparator { task: Task, task2: Task -> (task.id - task2.id).toInt() }
        tasksLD.value = tasks.sortedWith(lengthComparator).toList()
        Log.d("Log_App", "\t updateList() save this "+ getList().value.toString())
    }
    override fun getCountList() : Int{
        return tasks.size
    }
    override fun setCountId(countNew : Long){
        counterId = countNew
    }

    override fun setList(listNew: List<Task>) {
        tasks = listNew.toMutableList()
        updateList()
    }

}