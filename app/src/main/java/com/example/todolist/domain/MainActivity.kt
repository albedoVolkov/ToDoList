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
import com.example.todolist.data.ListRepositoryImpl
import com.example.todolist.presentation.TaskAdapter
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView
    private val list = GetListUseCase(ListRepositoryImpl)
    private lateinit var sharedPref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")
        setContentView(R.layout.activity_main)
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        initUI()
        init()
        getInfo()
    }

    override fun onStop() {
        super.onStop()
        Log.d("Log_App", "onStop called")
        saveData(list.getList())
    }
    private fun getInfo(){
        Log.d("Log_App", "getOldInfo called")
        if ( sharedPref.contains("info_tasks")){
            val savedValue = sharedPref.getString("info_tasks", "")
            if (savedValue == ""){
                Log.d("Log_App", "savedValue is empty")
            }else{
                val itemList = Gson().fromJson(savedValue, Array<Task>::class.java)
                for(item in itemList.toList()){
                    taskAdapter.addTask(item, taskAdapter.itemCount)
                }
            }
        }else{
            Log.d("Log_App", "sharedPref is empty")
        }

                val editor = sharedPref.edit()
                editor.clear()
                editor.apply()
    }
    private fun saveData(res : List<Task>){
        Log.d("Log_App", "saveData called")
        val editor = sharedPref.edit()
        val info = Gson().toJson(res).toString()
        editor.putString("info_tasks", info)
        editor.apply()
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
        recyclerView.setOnClickListener{}
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

