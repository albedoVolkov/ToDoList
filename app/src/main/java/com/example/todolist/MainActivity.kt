package com.example.todolist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var titleGot: String? = ""
    private var descriptionGot: String? = ""
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        init()
    }

    private fun init() {
        val tasks = ArrayList<Task>()
        taskAdapter = TaskAdapter(tasks)
        recyclerView = findViewById(R.id.taskRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TaskAdapter(tasks = tasks)
    }

    private fun initUI() {
        findViewById<View>(R.id.toolbar_1_button_add).setOnClickListener {
            val myIntent = Intent(this, ForCreatingItemActivity::class.java)
            startActivityForResult(myIntent, 100)
            Toast.makeText(this, "open", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.toolbar_1_button_settings).setOnClickListener { Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show() }
        findViewById<View>(R.id.toolbar_1_button_filter).setOnClickListener { Toast.makeText(this, "filter", Toast.LENGTH_SHORT).show() }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK && data != null)
        {
            Toast.makeText(this,"data",Toast.LENGTH_SHORT).show()
            titleGot = data.getStringExtra("title")
            descriptionGot = data.getStringExtra("description")

            if(titleGot != "" && descriptionGot != "")
            {
                taskAdapter.addTask(Task(taskAdapter.itemCount,title =  titleGot.toString(),description = descriptionGot.toString()))
            }
        }

    }

}

data class Task( var id: Int, val title: String, val description: String)

class TaskAdapter(private var tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


        class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
            val idTextView : TextView = itemView.findViewById(R.id.idTextView)
            val deleteView: View = itemView.findViewById(R.id.layout2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
            return TaskViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val task = tasks[position]
            holder.titleTextView.text = task.title
            holder.descriptionTextView.text = task.description
            holder.idTextView.text = task.id.toString()
            holder.deleteView.setOnLongClickListener{
                deleteTask(task)
                return@setOnLongClickListener true
            }
        }

        override fun getItemCount(): Int { return tasks.size }

        @SuppressLint("NotifyDataSetChanged")
        fun addTask(task: Task) {
            tasks.add(task)
            notifyDataSetChanged()
            refresh()
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun deleteTask(task: Task) {
            tasks.remove(task)
            notifyDataSetChanged()
            refresh()
        }

        private fun refresh() {
            val tasks2 = ArrayList<Task>()
            for((count, item) in tasks.withIndex()){
                item.id = count
                tasks2.add(item)
            }
            tasks.clear()
            tasks.addAll(tasks2)
            tasks2.clear()
        }

}