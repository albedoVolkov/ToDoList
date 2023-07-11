package com.example.todolist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.domain.list.Status
import com.example.todolist.domain.list.Task
import java.lang.RuntimeException


class TaskAdapter(private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var list: List<Task> = listOf()
    interface ItemClickListener {
        fun onItemClick(id: Int, itemView: View)
        fun onLongClick(id: Int, itemView: View)
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
                100 -> {titleTextView = itemView.findViewById(R.id.item_done_textView_1)
                descriptionTextView = itemView.findViewById(R.id.item_done_textView_2)}

                200 -> {titleTextView = itemView.findViewById(R.id.item_not_done_textView_1)
                descriptionTextView = itemView.findViewById(R.id.item_not_done_textView_2)}

                300 -> {titleTextView = itemView.findViewById(R.id.item_postponed_textView_1)
                    descriptionTextView = itemView.findViewById(R.id.item_postponed_textView_2)}
            }

            itemView.setOnClickListener { itemClickListener.onItemClick(list[adapterPosition].id, itemView) }
            itemView.setOnLongClickListener{ itemClickListener.onLongClick(list[adapterPosition].id,itemView); return@setOnLongClickListener true }
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
        val task = list[position]
        holder.titleTextView.text = task.title
        holder.descriptionTextView.text = task.description
    }

    fun setList(listNew : List<Task>){
        list = listNew
        notifyDataSetChanged()
    }
}