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
import com.example.todolist.data.Task
import com.example.todolist.presentation.TaskAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView
    private val list = GetListUseCase()
    private lateinit var sharedPref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")
        setContentView(R.layout.activity_main)
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        initUI()
        init(list.getList())
        getOldInfo()
    }

    override fun onStop() {
        super.onStop()
        Log.d("Log_App", "onStop called")
        saveData(list.getList() as ArrayList<Task>)
    }
    private fun getOldInfo(){
        Log.d("Log_App", "getOldInfo called")
        if ( sharedPref.contains("info_tasks")){
            val savedValue = sharedPref.getString("info_tasks", "")
            if (savedValue == ""){
                Log.d("Log_App", "savedValue is empty")
            }else{
                val itemList = Gson().fromJson(savedValue, Array<Task>::class.java)
                for(item in itemList.toMutableList()){
                    taskAdapter.addTask(item, taskAdapter.itemCount)
                }
            }
        }else{
            Log.d("Log_App", "sharedPref is empty")
        }
    }
    private fun saveData(res : ArrayList<Task>){
        Log.d("Log_App", "saveData called")
        val editor = sharedPref.edit()
        val info =  Gson().toJson(res).toString()
        editor.putString("info_tasks", info)
        editor.apply()
    }

    private fun init(list : MutableList<Task>) {
        taskAdapter = TaskAdapter(list)
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
            val t = Task(View.generateViewId(),
                title =  data.getStringExtra("title").toString(),
                description = data.getStringExtra("description").toString(),
                enabled = true, importance = data.getIntExtra("importance",0))
            taskAdapter.addTask(t,taskAdapter.itemCount)

            Log.d("Log_App","MainActivity take info")
        }
    }

}

