package com.example.todolist.presentation

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.ListRepositoryImpl
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.domain.helpers.Status
import com.example.todolist.domain.helpers.Task
import com.example.todolist.domain.list.useCases.GetCountListUseCase


class MainActivity : AppCompatActivity(), FromTaskActivityToMainActivity,TaskAdapter.ItemClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initSwipe()
        initUI()
    }

    override fun onStop() {
        super.onStop()
        if (viewModel.getList().value != null){
            viewModel.saveData(viewModel.getList().value!!,getSharedPreferences("MyPrefs", Context.MODE_PRIVATE))
        }
        else{
            viewModel.saveData(listOf(), getSharedPreferences("MyPrefs", Context.MODE_PRIVATE))
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData(getSharedPreferences("MyPrefs", Context.MODE_PRIVATE))
    }


    private fun initSwipe() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) { viewModel.delete(viewModel.getTaskById(viewModel.getList().value!![viewHolder.adapterPosition].id)) }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewMainActivity)
    }

    private fun init() {
        val taskAdapter = TaskAdapter(this)
        binding.recyclerViewMainActivity.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMainActivity.setHasFixedSize(true)
        binding.recyclerViewMainActivity.adapter = taskAdapter

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getList().observe(this
          ) {
            list ->
            taskAdapter.setList(list.toList())
            Log.d("Log_App", list.toString())
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.todolist.R.menu.popup_menu_toolbar_1,menu)
        return true
    }

    private fun initUI() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) { viewModel.delete(viewModel.getTaskById(viewModel.getList().value!![viewHolder.adapterPosition].id)) }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewMainActivity)

        binding.toolBarActivityMain.textView1Toolbar1.setOnClickListener {
            Toast.makeText(this, GetCountListUseCase(ListRepositoryImpl).execute().toString(), Toast.LENGTH_SHORT).show()
        }

        binding.toolBarActivityMain.view1Toolbar1.setOnClickListener {
            binding.toolBarActivityMain.root.visibility = View.INVISIBLE
            openFrag(TaskFragment(), R.id.frameLayout_main_activity)
        }

        binding.toolBarActivityMain.view2Toolbar1.setOnClickListener {
            val popup = PopupMenu(this, binding.toolBarActivityMain.view2Toolbar1)
            popup.inflate(R.menu.popup_menu_toolbar_1)
            popup.show()
            popup.setOnMenuItemClickListener{
                when (it.itemId){

                    R.id.popupmenu_toolbar_1_filter -> {
                        Log.d("Log_App", "filter")
                        Toast.makeText(this, "filter", Toast.LENGTH_SHORT).show()
                    }

                    R.id.popupmenu_toolbar_1_delete_all -> {
                        Log.d("Log_App", "delete all")
                        Toast.makeText(this, "delete all", Toast.LENGTH_SHORT).show()
                        viewModel.deleteAll()
                    }

                    R.id.popupmenu_toolbar_1_info -> {
                        Log.d("Log_App", "info about us")
                        Toast.makeText(this, "info about us", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }

    }


    override fun onItemClick(id: Long,itemView: View) {
        val bundle = Bundle()
        bundle.putString("titleFromMainActivity",viewModel.getTaskById(id).title)
        bundle.putString("descriptionFromMainActivity",viewModel.getTaskById(id).description)
        bundle.putString("idFromMainActivity",id.toString())
        val fragment = TaskFragment()
        fragment.arguments = bundle
        openFrag(fragment, com.example.todolist.R.id.frameLayout_main_activity)
    }


    override fun onLongClick(id: Long,itemView: View) {
        val popup = PopupMenu(this, itemView)
        val task = viewModel.getTaskById(id)
        popup.inflate(com.example.todolist.R.menu.popup_menu_status_1)
        popup.show()
        popup.setOnMenuItemClickListener{
            when (it.itemId){
                com.example.todolist.R.id.popupmenu_status_1_postponed -> {
                    viewModel.changeStatusState(task, Status.Postponed)
                }
                com.example.todolist.R.id.popupmenu_status_1_not_done -> {
                    viewModel.changeStatusState(task, Status.NotDone)
                }
                com.example.todolist.R.id.popupmenu_status_1_done -> {
                    viewModel.changeStatusState(task, Status.Done)
                }
            }
            true
        }
    }


    private fun openFrag(fragment : Fragment, id : Int){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(id,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun communicate(title: String, description: String) {
        binding.toolBarActivityMain.root.visibility = View.VISIBLE
        viewModel.insert(Task(title,description))
    }

    override fun communicate(title: String, description: String, idOld: Long) {
        binding.toolBarActivityMain.root.visibility = View.VISIBLE
        viewModel.edit(Task(title,description,id = idOld))
    }

    override fun communicate() {
        binding.toolBarActivityMain.root.visibility = View.VISIBLE
    }


}


