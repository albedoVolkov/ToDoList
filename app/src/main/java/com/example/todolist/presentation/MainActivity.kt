package com.example.todolist.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SwitchCompat
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


class MainActivity : AppCompatActivity(), FromTaskActivityToMainActivity,
    TaskAdapter.ItemClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter : TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Log_App", "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initSwipe()
        initUI()
        viewModel.getData(getSharedPreferences("MyPrefs", Context.MODE_PRIVATE))
    }
    override fun onStop() {
        super.onStop()
        Log.d("Log_App", "onStop")
        if (viewModel.getList().value != null){
            viewModel.saveData(viewModel.getList().value!!,getSharedPreferences("MyPrefs", Context.MODE_PRIVATE))
        }
        else{
            viewModel.saveData(listOf(), getSharedPreferences("MyPrefs", Context.MODE_PRIVATE))
        }
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
        taskAdapter = TaskAdapter(this)
        binding.recyclerViewMainActivity.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMainActivity.setHasFixedSize(true)
        binding.recyclerViewMainActivity.adapter = taskAdapter

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getList().observe(this
          ) { list ->
            taskAdapter.setList(list.toList())
            Log.d("Log_App", "showing list now = $list.toString()")
        }
    }




    private fun initUI() {

        var q = false
        var q2 = false

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
            val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout_main_activity, TaskFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.toolBarActivityMain.view2Toolbar1.setOnClickListener {
            val popup = PopupMenu(this, binding.toolBarActivityMain.view2Toolbar1)
            popup.inflate(R.menu.popup_menu_toolbar_1)
            popup.show()
            popup.setOnMenuItemClickListener{
                when (it.itemId){

                    R.id.popupmenu_toolbar_1_filter -> {
                        q2 = !q2
                        if(q2){
                            popup.menu.findItem(R.id.popupmenu_toolbar_1_filter).icon = AppCompatResources.getDrawable(applicationContext,R.drawable.arrow_up)
                        }else{
                            popup.menu.findItem(R.id.popupmenu_toolbar_1_filter).icon = AppCompatResources.getDrawable(applicationContext,R.drawable.arrow_down)
                        }
                        Toast.makeText(this, ""+q2, Toast.LENGTH_SHORT).show()
                    }

                    R.id.popupmenu_toolbar_1_delete_all -> {
                        viewModel.deleteAll()
                    }

                    R.id.popupmenu_toolbar_1_hide_completed_items -> {
                        q = !q
                        taskAdapter.setStatusHideCompleted(q)
                        refresh()
                        Toast.makeText(this, ""+q, Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }

    }



    fun refresh(){
        taskAdapter.setList(viewModel.getList().value!!.toList())
    }


    override fun onItemClick(id: Long,itemView: View) {
        val intent = Intent(this@MainActivity, TaskViewActivity::class.java)
        intent.putExtra("titleFromMainActivity", viewModel.getTaskById(id).title)
        intent.putExtra("descriptionFromMainActivity", viewModel.getTaskById(id).description)
        intent.putExtra("idFromMainActivity", id)
        startActivityForResult(intent,1)
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
                com.example.todolist.R.id.popupmenu_status_1_not_completed -> {
                    viewModel.changeStatusState(task, Status.NotCompleted)
                }
                com.example.todolist.R.id.popupmenu_status_1_completed -> {
                    viewModel.changeStatusState(task, Status.Completed)
                }
            }
            true
        }
    }






    override fun communicate(title: String, description: String) {
        binding.toolBarActivityMain.root.visibility = View.VISIBLE
        viewModel.insert(Task(title,description))
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val titleNew = data!!.getStringExtra("titleFromTaskViewActivity")
                val descriptionNew = data.getStringExtra("descriptionFromTaskViewActivity")
                val idOld = data.getLongExtra("idFromTaskViewActivity", -1)
                viewModel.edit(Task(titleNew!!,descriptionNew!!,id = idOld))
            }
        }
    }

}


