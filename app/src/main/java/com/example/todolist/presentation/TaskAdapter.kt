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
    private var hideCompleted = false

    interface ItemClickListener {
        fun onItemClick(id: Long, itemView: View)
        fun onLongClick(id: Long, itemView: View)
    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        return when(item.status){
            Status.Completed -> {100}
            Status.NotCompleted -> { 200 }
            Status.Postponed -> { 300 }
        }
    }

    override fun getItemCount(): Int { return list.size }

    inner class TaskViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        private lateinit var titleTextView: TextView
        init {
            when(viewType){
                100 -> {titleTextView = itemView.findViewById(R.id.textView_1_item_completed)}

                200 -> {titleTextView = itemView.findViewById(R.id.textView_1_item_not_completed)}

                300 -> {titleTextView = itemView.findViewById(R.id.textView_1_item_postponed)}
            }

            itemView.setOnClickListener { itemClickListener.onItemClick(list[adapterPosition].id, itemView) }
            itemView.setOnLongClickListener{ itemClickListener.onLongClick(list[adapterPosition].id,itemView); return@setOnLongClickListener true }
        }


        fun bind(taskModel: Task) {
            titleTextView.text = taskModel.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return when(viewType){
            100 -> {TaskViewHolder(itemView.inflate(R.layout.item_completed, parent, false),viewType)}

            200 -> {TaskViewHolder(itemView.inflate(R.layout.item_not_completed, parent, false),viewType)}

            300 -> {TaskViewHolder(itemView.inflate(R.layout.item_postponed, parent, false),viewType)}

            else -> {throw RuntimeException("error in onCreateViewHolder")}
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setList(listNew : List<Task>){
        if(hideCompleted){
            val listWithoutCompletedItems = mutableListOf<Task>()
                for (item in listNew) {
                    if (item.status != Status.Completed) {
                        listWithoutCompletedItems.add(item)
                    }
                }
            list = listWithoutCompletedItems
            notifyDataSetChanged()
        }else{
            list = listNew
            notifyDataSetChanged()
        }
    }
    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    fun setStatusHideCompleted(bool : Boolean){
        hideCompleted = bool
    }
}