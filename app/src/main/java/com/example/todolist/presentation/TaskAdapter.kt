package com.example.todolist.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.Task

class TaskAdapter(private var tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


        class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
            val idTextView : TextView = itemView.findViewById(R.id.idTextView)
            val deleteView: View = itemView.findViewById(R.id.layout2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_item, parent, false)
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


        fun addTask(task: Task, position: Int) {
            tasks.add(task)
            notifyItemInserted(position)
            refreshData()
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun deleteTask(task: Task) {
            tasks.remove(task)
            notifyDataSetChanged()
            refreshData()
        }

        private fun refreshData() {
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