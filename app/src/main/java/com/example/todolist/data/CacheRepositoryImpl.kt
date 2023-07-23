package com.example.todolist.data

import android.content.SharedPreferences
import android.util.Log
import com.example.todolist.domain.cache.CacheRepository
import com.example.todolist.domain.list.useCases.GetCountListUseCase
import com.example.todolist.domain.list.useCases.SetCountIdUseCase
import com.example.todolist.domain.list.useCases.SetListUseCase
import com.example.todolist.domain.helpers.Task
import com.google.gson.Gson

object CacheRepositoryImpl : CacheRepository {
    override fun getData(sharedPref : SharedPreferences){
        Log.d("Log_App", "CacheData : getData called")

        //setting additional info
        val savedValueCountId = sharedPref.getString("countId", "0")
        SetCountIdUseCase(ListRepositoryImpl).execute(savedValueCountId!!.toLong())
        //setting list info
        if ( sharedPref.contains("info_tasks")){
            val savedValueInfoTasks = sharedPref.getString("info_tasks", "")
            if (savedValueInfoTasks == ""){
                Log.d("Log_App", "CacheData : getData : sharedPref : savedValue is empty")
            }else{
                val listTasks = Gson().fromJson(savedValueInfoTasks, Array<Task>::class.java)
                SetListUseCase(ListRepositoryImpl).execute(listTasks.toList())
            }
        }else{
            Log.d("Log_App","CacheData : getData : sharedPref doesn't contain info_tasks")
        }

        //cleaning
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    override fun saveData(res : List<Task>, sharedPref : SharedPreferences){
        Log.d("Log_App", "CacheData : saveData called")
        val editor = sharedPref.edit()
        val data = Gson().toJson(res).toString()
        editor.putString("info_tasks", data)
        editor.putString("countId", GetCountListUseCase(ListRepositoryImpl).execute().toString())
        editor.apply()
    }
}