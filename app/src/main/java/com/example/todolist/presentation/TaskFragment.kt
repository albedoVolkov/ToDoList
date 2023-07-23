package com.example.todolist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.todolist.R
import com.example.todolist.databinding.ActivityTaskBinding


class TaskFragment : Fragment() {

    private lateinit var binding : ActivityTaskBinding
    private lateinit var keyboard : InputMethodManager
    private var  titleFromActivity : String? = null
    private var  descriptionFromActivity : String? = null
    private var  idFromMainActivity : Long? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {

        binding = ActivityTaskBinding.inflate(inflater, container, false)
        keyboard = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        openKeyboard()
        //val activity = requireActivity() as AppCompatActivity
        //activity.setSupportActionBar(binding.root.findViewById(R.id.toolbar_task_fragment))

        if (arguments?.getString("idFromMainActivity") != null &&
            arguments?.getString("titleFromMainActivity") != null &&
            arguments?.getString("descriptionFromMainActivity") != null) {
            titleFromActivity = arguments?.getString("titleFromMainActivity").toString()
            descriptionFromActivity = arguments?.getString("descriptionFromMainActivity").toString()
            idFromMainActivity = arguments?.getString("idFromMainActivity").toString().toLong()
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!this::binding.isInitialized) {
            throw Exception("Binding isn't initialised TaskFragment")
        }

        if (binding.editView1TaskActivity.text.toString() != "") {
            markButtonEnabled(binding.button1TaskActivity)
        } else { markButtonDisable(binding.button1TaskActivity) }

        if (titleFromActivity != null && descriptionFromActivity != null) {
            binding.editView1TaskActivity.setText(titleFromActivity)
            binding.editView2TaskActivity.setText(descriptionFromActivity)
        }

        binding.editView2TaskActivity.afterTextChanged {
            if (binding.editView2TaskActivity.text.toString() != "")
            { markButtonEnabled(binding.button1TaskActivity)
            } else { markButtonDisable(binding.button1TaskActivity) }
        }

        binding.centreTaskActivity.setOnClickListener {
            (activity as FromTaskActivityToMainActivity).communicate()
            closeFragment() }//close fragment without result

        binding.toolbarTaskFragment.view1Toolbar2.setOnClickListener {
            (activity as FromTaskActivityToMainActivity).communicate()
            closeFragment() }//close fragment without result


        binding.button1TaskActivity.setOnClickListener {
            if(idFromMainActivity != null){
                (activity as FromTaskActivityToMainActivity)
                    .communicate(
                        binding.editView2TaskActivity.text.toString(),
                        binding.editView1TaskActivity.text.toString(),
                        idFromMainActivity!!)// send information
            }else{
                (activity as FromTaskActivityToMainActivity)
                    .communicate(
                        binding.editView1TaskActivity.text.toString(),
                        binding.editView2TaskActivity.text.toString())// send information
            }
            closeFragment()
        }
    }



    private fun closeFragment(){
        val transaction = activity?.supportFragmentManager?.beginTransaction() ?: throw Exception("transaction in TaskFragment is null")
        closeKeyboard()
        transaction.remove(this)
        transaction.commit()
    }
    private fun openKeyboard(){
        keyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }
    private fun closeKeyboard(){
        keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
    private fun markButtonDisable(view: View) {
        view.isEnabled = false
        view.background.setTint(ContextCompat.getColor(view.context, R.color.color_background_creating_item_button_disabled))
    }
    private fun markButtonEnabled(view: View) {
        view.isEnabled = true
        view.background.setTint(ContextCompat.getColor(view.context, R.color.color_background_creating_item_button_enabled))
    }
    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable?) { afterTextChanged.invoke(editable.toString()) }
        })
    }
}