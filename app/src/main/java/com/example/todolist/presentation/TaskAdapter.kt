package com.example.todolist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.domain.helpers.Status
import com.example.todolist.domain.helpers.Task
import java.lang.RuntimeException


class TaskAdapter( private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var list = listOf<Task>()

    interface ItemClickListener {
        fun onItemClick(id: Long, itemView: View)
        fun onLongClick(id: Long, itemView: View)
    }
    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        return when(item.status){
            Status.Done -> { 100 }
            Status.NotDone -> { 200 }
            Status.Postponed -> { 300 }
        }
    }

    override fun getItemCount(): Int { return list.size }

    inner class TaskViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        lateinit var titleTextView: TextView
        lateinit var descriptionTextView: TextView
        init {
            when(viewType){
                100 -> {titleTextView = itemView.findViewById(R.id.textView_1_item_done)
                descriptionTextView = itemView.findViewById(R.id.textView_2_item_done)}

                200 -> {titleTextView = itemView.findViewById(R.id.textView_1_item_not_done)
                descriptionTextView = itemView.findViewById(R.id.textView_2_item_not_done)}

                300 -> {titleTextView = itemView.findViewById(R.id.textView_1_item_postponed)
                    descriptionTextView = itemView.findViewById(R.id.textView_2_item_postponed)}
            }

            itemView.setOnClickListener { itemClickListener.onItemClick(list[adapterPosition].id, itemView) }
            itemView.setOnLongClickListener{ itemClickListener.onLongClick(list[adapterPosition].id,itemView); return@setOnLongClickListener true }
        }


        fun bind(taskModel: Task) {
                titleTextView.text = taskModel.title
                descriptionTextView.text = taskModel.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return when(viewType){
            100 -> {TaskViewHolder(itemView.inflate(R.layout.item_done, parent, false),viewType)}

            200 -> {TaskViewHolder(itemView.inflate(R.layout.item_not_done, parent, false),viewType)}

            300 -> {TaskViewHolder(itemView.inflate(R.layout.item_postponed, parent, false),viewType)}
            else -> {throw RuntimeException("error in onCreateViewHolder")}
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setList(listNew : List<Task>){
        list = listNew
        notifyDataSetChanged()
    }
    override fun getItemId(position: Int): Long {
        return list[position].id
    }
}