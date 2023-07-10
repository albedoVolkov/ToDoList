package com.example.todolist.domain.cache

import android.content.SharedPreferences
import com.example.todolist.domain.list.Task

interface CacheRepository {
    fun getData(sharedPref : SharedPreferences)
    fun saveData(res : List<Task>, sharedPref : SharedPreferences)
}