package com.example.todolist.domain.cache

import android.content.SharedPreferences
import android.util.Log
import com.example.todolist.domain.list.Task

class SaveDataUseCase(private val cacheRepository: CacheRepository) {
    fun execute(res : List<Task>, sharedPref : SharedPreferences) {
        Log.d("Log_App", "SaveDataUseCase : execute  called with this item: \n $res")
        cacheRepository.saveData(res, sharedPref)
    }
}