package com.example.todolist.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.ListRepositoryImpl
import com.example.todolist.domain.AddTaskUseCase
import com.example.todolist.domain.DeleteTaskUseCase
import com.example.todolist.domain.ForCreatingItemActivity
import com.example.todolist.domain.Task
import com.example.todolist.domain.GetListUseCase
import com.example.todolist.domain.MainActivity

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
        class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.activity_2_textView_1)
            val descriptionTextView: TextView = itemView.findViewById(R.id.activity_2_textView_2)
            val enabledTextView : TextView = itemView.findViewById(R.id.activity_2_textView_3)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_2, parent, false)
            return TaskViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val task = GetListUseCase(listRepository = ListRepositoryImpl).getList()[position]
            holder.titleTextView.text = task.title
            holder.descriptionTextView.text = task.description

            if (task.importance == -10592674){
                holder.enabledTextView.visibility = View.INVISIBLE
            } else {
                holder.enabledTextView.background.setTint(task.importance)
            }

            if (task.enabled) {
                holder.itemView.background.alpha = 255
            }else{
                holder.itemView.background.alpha = 130
            }

            holder.itemView.setOnLongClickListener{
                deleteTask(task,position)
                Log.d("Log_App","TaskAdapter deletes item")
                return@setOnLongClickListener true
            }
            holder.itemView.setOnClickListener{
                Log.d("Log_App","TaskAdapter changes enable of item")
                if (task.enabled) {
                    // disabled
                    holder.itemView.background.alpha = 130

                } else {
                    // enabled
                    holder.itemView.background.alpha = 255

                }
                task.enabled = !task.enabled
            }
        }
        override fun getItemCount(): Int { return GetListUseCase(listRepository = ListRepositoryImpl).getList().size }

        @SuppressLint("NotifyDataSetChanged")
        fun addTask(task : Task, position: Int) {
            Log.d("Log_App", "TaskAdapter : addTask  called")
            AddTaskUseCase(ListRepositoryImpl).addTask(task)
            Log.d("Log_App", "TaskAdapter : addTask  is successful\n")
            Log.d("Log_App", " ")
            notifyItemInserted(position)
            notifyItemRangeChanged(position, itemCount)
            notifyDataSetChanged()
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun deleteTask(task : Task, position: Int) {
            Log.d("Log_App", "deleteTask called")
            DeleteTaskUseCase(ListRepositoryImpl).deleteTask(task)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
            notifyDataSetChanged()
        }
}