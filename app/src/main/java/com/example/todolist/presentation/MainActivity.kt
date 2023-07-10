package com.example.todolist.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.CacheRepositoryImpl
import com.example.todolist.data.ListRepositoryImpl
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.domain.cache.GetDataUseCase
import com.example.todolist.domain.cache.SaveDataUseCase
import com.example.todolist.domain.list.GetCountListUseCase
import com.example.todolist.domain.list.Status
import com.example.todolist.domain.list.Task


class MainActivity : AppCompatActivity(), FromCreatingItemFragmentToActivity,TaskAdapter.ItemClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPref : SharedPreferences
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initUI()
    }

    override fun onStop() {
        super.onStop()
        if (viewModel.getList().value != null){
            viewModel.saveData(viewModel.getList().value!!,sharedPref)
        }
        else{
            viewModel.saveData(listOf<Task>(), sharedPref)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData(sharedPref)
    }


    private fun init() {
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        recyclerView = binding.activityMainRecyclerView1
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        taskAdapter = TaskAdapter(this)
        recyclerView.adapter = taskAdapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getList().observe(this,object : Observer<List<Task>> {
            override fun onChanged(list: List<Task>) {
                taskAdapter.setList(list.toList())
                Log.d("Log_App", list.toString())
            }
        })
    }


    private fun initUI() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) { viewModel.delete(viewModel.getTaskById(viewModel.getList().value!![viewHolder.adapterPosition].id)) }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        binding.activityMainToolBar1.toolBarTextView1.setOnClickListener {
            Toast.makeText(this, GetCountListUseCase(ListRepositoryImpl).execute().toString(), Toast.LENGTH_SHORT).show()
        }

        binding.activityMainToolBar1.toolBarView1.setOnClickListener {
            openFrag(CreatingItemFragment(), com.example.todolist.R.id.activity_main_frameLayout_1)
        }

        binding.activityMainToolBar1.toolBarView2.setOnClickListener {
            val popup = PopupMenu(this, binding.activityMainToolBar1.toolBarView2)
            popup.inflate(com.example.todolist.R.menu.popup_menu_toolbar)
            popup.show()
            popup.setOnMenuItemClickListener{
                when (it.itemId){


                    com.example.todolist.R.id.popupmenu_toolbar_filter -> {
                        Log.d("Log_App", "filter")
                        Toast.makeText(this, "filter", Toast.LENGTH_SHORT).show()
                    }


                    com.example.todolist.R.id.popupmenu_toolbar_delete_all -> {
                        Log.d("Log_App", "delete all")
                        Toast.makeText(this, "delete all", Toast.LENGTH_SHORT).show()
                        viewModel.deleteAll()
                    }


                    com.example.todolist.R.id.popupmenu_toolbar_info -> {
                        Log.d("Log_App", "info about us")
                        Toast.makeText(this, "info about us", Toast.LENGTH_SHORT).show()
                    }


                }
                true
            }
        }

    }


    override fun onItemClick(id: Int) {

    }


    override fun onLongClick(id: Int,itemView: View) {
        val popup = PopupMenu(this, itemView)
        val task = viewModel.getTaskById(id)
        popup.inflate(com.example.todolist.R.menu.popup_menu_1)
        popup.show()
        popup.setOnMenuItemClickListener{
            when (it.itemId){
                com.example.todolist.R.id.popupmenu_1_postponed -> {
                    viewModel.changeStatusState(task,Status.Postponed)
                }
                com.example.todolist.R.id.popupmenu_1_not_done -> {
                    viewModel.changeStatusState(task,Status.NotDone)
                }
                com.example.todolist.R.id.popupmenu_1_done -> {
                    viewModel.changeStatusState(task,Status.Done)
                }
            }
            true
        }
    }


    fun openFrag(fragment : Fragment, id : Int){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(id,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun communicate(title: String, description: String, ) {
        viewModel.insert(Task(title,description))
    }


}
