package com.example.todolist.domain.cache

import android.content.SharedPreferences
import android.util.Log

class GetDataUseCase(private val cacheRepository: CacheRepository) {
    fun execute(sharedPref : SharedPreferences) {
        Log.d("Log_App", "GetDataUseCase : execute  called")
        cacheRepository.getData(sharedPref)
    }
}