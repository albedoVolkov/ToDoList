package com.example.todolist.domain

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.Task
import com.example.todolist.presentation.TaskAdapter

class MainActivity : AppCompatActivity() {
    private var titleGot: String = ""
    private var descriptionGot: String = ""
    private var importanceGot: Int = 0
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = GetListUseCase()
        initUI()
        init(list.getShopList())
    }

    private fun init(list : MutableList<Task>) {
        taskAdapter = TaskAdapter(list)
        recyclerView = findViewById(R.id.activity_1_recyclerView_1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TaskAdapter(tasks = list)
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
        if(requestCode == 100 && resultCode == RESULT_OK && data != null)
        {
            titleGot = data.getStringExtra("title").toString()
            descriptionGot = data.getStringExtra("description").toString()
            importanceGot = data.getIntExtra("importance",0)
            taskAdapter.addTask(Task(View.generateViewId(),
                title =  titleGot.toString(),
                description = descriptionGot.toString(),
                enabled = true, importance = importanceGot.toInt()),taskAdapter.itemCount)
        }
    }
}

