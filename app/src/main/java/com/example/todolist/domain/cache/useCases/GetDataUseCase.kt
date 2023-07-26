package com.example.todolist.domain.cache.useCases

import android.content.SharedPreferences
import android.util.Log
import com.example.todolist.domain.cache.CacheRepository

class GetDataUseCase(private val cacheRepository: CacheRepository) {
    fun execute(sharedPref : SharedPreferences) {
        cacheRepository.getData(sharedPref)
    }
}