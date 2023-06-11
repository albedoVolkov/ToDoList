package com.example.todolist.presentation

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.Task

class TaskAdapter(private var tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

        class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.activity_2_textView_1)
            val descriptionTextView: TextView = itemView.findViewById(R.id.activity_2_textView_2)
            val view : View = itemView.findViewById(R.id.activity_2_layout_1)
            val warningTextView : TextView = itemView.findViewById(R.id.activity_2_textView_3)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_2, parent, false)
            return TaskViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val task = tasks[position]
            holder.titleTextView.text = task.title
            holder.descriptionTextView.text = task.description

            if (task.importance == -10592674){
                holder.warningTextView.visibility = View.INVISIBLE
            } else {
                holder.warningTextView.background.setTint(task.importance)
            }


            if (task.enabled) {
                holder.view.background.alpha = 255
            }else{
                holder.view.background.alpha = 130
            }

            holder.view.setOnLongClickListener{
                deleteTask(task,position)
                Log.d("Log_App","TaskAdapter deletes item")
                return@setOnLongClickListener true
            }
            holder.view.setOnClickListener{
                Log.d("Log_App","TaskAdapter changes enable of item")
                if (task.enabled) {
                    // disabled
                    holder.view.background.alpha = 130

                } else {
                    // enabled
                    holder.view.background.alpha = 255

                }
                task.enabled = !task.enabled
            }
        }

        override fun getItemCount(): Int { return tasks.size }

        @SuppressLint("NotifyDataSetChanged")
        fun addTask(task : Task, position: Int) {
            tasks.add(task)
            Log.d("Log_App", "addTask - supply")
            Log.d("Log_App", "        list:")
            for (item in tasks){
                Log.d("Log_App", "        $item")
            }
            notifyItemInserted(position)
            notifyItemRangeChanged(position, tasks.size)
            notifyDataSetChanged()

        }

        @SuppressLint("NotifyDataSetChanged")
        private fun deleteTask(task : Task, position: Int) {
            tasks.remove(task)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tasks.size)
            notifyDataSetChanged()
        }
}