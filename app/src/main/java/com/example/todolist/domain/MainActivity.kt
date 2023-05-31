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
    private var titleGot: String? = ""
    private var descriptionGot: String? = ""
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
        recyclerView = findViewById(R.id.taskRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TaskAdapter(tasks = list)
    }

    private fun initUI() {
        findViewById<View>(R.id.toolbar_1_button_add).setOnClickListener {
            val myIntent = Intent(this, ForCreatingItemActivity::class.java)
            startActivityForResult(myIntent, 100)
        }
        findViewById<View>(R.id.toolbar_1_button_list).setOnClickListener { Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.toolbar_1_button_filter).setOnClickListener { Toast.makeText(this, "filter", Toast.LENGTH_SHORT).show() }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK && data != null)
        {
            titleGot = data.getStringExtra("title")
            descriptionGot = data.getStringExtra("description")
            taskAdapter.addTask(Task(taskAdapter.itemCount,title =  titleGot.toString(), enabled = true,description = descriptionGot.toString()),taskAdapter.itemCount)
        }

    }

}

