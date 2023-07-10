package com.example.todolist.presentation

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.databinding.CreatingItemBinding


class CreatingItemFragment : Fragment() {

    private lateinit var binding : CreatingItemBinding
    private val handler = Handler()
    private lateinit var  title: EditText
    private lateinit var  description: EditText
    override fun onCreateView(
        inflater : LayoutInflater,container : ViewGroup?,savedInstanceState : Bundle?): View {
        binding = CreatingItemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.creatingItemButton1.setOnClickListener {
                Log.d("Log_App", "ForCreatingItem finish")
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.remove(this)
                transaction?.commit()
            }//back

            binding.creatingItemButton2.setOnClickListener {

                title = binding.creatingItemEditView1
                description = binding.creatingItemEditView2

                if (title.text.toString() != "" && description.text.toString() != "") {//test
                    Log.d("Log_App", "ForCreatingItem send info")

                    (activity as FromCreatingItemFragmentToActivity).communicate(
                        title.text.toString(),
                        description.text.toString()
                    )// send information

                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.remove(this)
                    transaction?.commit()// end transaction

                } else {
                    if (title.text.toString() == "") {
                        title.setHintTextColor(resources.getColor(com.example.todolist.R.color.color_incorrect_hint_creating_item_editViews))
                        title.hint = getString(com.example.todolist.R.string.required)
                        handler.postDelayed({
                            title.setHintTextColor(resources.getColor(com.example.todolist.R.color.color_text_creating_item_editViews))
                            title.hint = getString(com.example.todolist.R.string.activity_2_hint_Title)
                        }, 2000)
                    }

                    if (description.text.toString() == "") {
                        description.setHintTextColor(resources.getColor(com.example.todolist.R.color.color_incorrect_hint_creating_item_editViews))
                        description.hint = getString(com.example.todolist.R.string.required)
                        handler.postDelayed({
                            description.setHintTextColor(resources.getColor(com.example.todolist.R.color.color_text_creating_item_editViews))
                            description.hint = getString(com.example.todolist.R.string.description_task_item)
                        }, 2000)

                    }
                }
            }//send result and back


    }

}