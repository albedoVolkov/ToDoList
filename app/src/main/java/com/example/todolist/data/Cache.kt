package com.example.todolist.data

import android.content.SharedPreferences
import android.util.Log
import com.example.todolist.domain.AddTaskUseCase
import com.example.todolist.domain.Task
import com.google.gson.Gson

class CacheData {
    fun getData(sharedPref : SharedPreferences){
        Log.d("Log_App", "CacheData : getData called")
        //getting
        if ( sharedPref.contains("info_tasks")){
            val savedValue = sharedPref.getString("info_tasks", "")
            if (savedValue == ""){
                Log.d("Log_App", "CacheData : getData : sharedPref : savedValue is empty")
            }else{
                val listTasks = Gson().fromJson(savedValue, Array<Task>::class.java)
                for(item in listTasks.toList()){
                    AddTaskUseCase(ListRepositoryImpl).addTask(item)
                }
            }
        }else{
            Log.d("Log_App","CacheData : getData : sharedPref doesn't contain info_tasks")
        }

        //cleaning
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    fun saveData(res : List<Task>, sharedPref : SharedPreferences){
        Log.d("Log_App", "CacheData : saveData called")
        val editor = sharedPref.edit()
        val data = Gson().toJson(res).toString()
        editor.putString("info_tasks", data)
        editor.apply()
    }
}