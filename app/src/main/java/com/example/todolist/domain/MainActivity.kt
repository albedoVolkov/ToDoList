package com.example.todolist.domain

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.CacheData
import com.example.todolist.data.ListRepositoryImpl
import com.example.todolist.presentation.TaskAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView
    private val list = GetListUseCase(ListRepositoryImpl)
    private lateinit var sharedPref : SharedPreferences
    private val cache = CacheData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")
        setContentView(R.layout.activity_main)
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        initUI()
        init()
        cache.getData(sharedPref)
    }

    override fun onStop() {
        super.onStop()
        Log.d("Log_App", "onStop called")
        cache.saveData(list.getList(),sharedPref)
    }


    private fun init() {
        taskAdapter = TaskAdapter()
        recyclerView = findViewById(R.id.activity_1_recyclerView_1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter
    }

    private fun initUI() {
        findViewById<View>(R.id.activity_4_view_1).setOnClickListener {
            val myIntent = Intent(this, ForCreatingItemActivity::class.java)
            startActivityForResult(myIntent, 100)
        }
        findViewById<View>(R.id.activity_4_view_3).setOnClickListener { Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.activity_4_view_2).setOnClickListener { Toast.makeText(this, "filter", Toast.LENGTH_SHORT).show() }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("MainActivity", "onActivityResult called")
        if(requestCode == 100 && resultCode == RESULT_OK && data != null)
        {
            val task = Task(
                title =  data.getStringExtra("title").toString(),
                description = data.getStringExtra("description").toString(),
                enabled = true, importance = data.getIntExtra("importance",0))
            Log.d("Log_App", "onActivityResult task : $task")
            taskAdapter.addTask(task,taskAdapter.itemCount)
            Log.d("Log_App", "MainActivity : onActivityResult : addTask  is successful")
        }
    }

}

