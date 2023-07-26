package com.example.todolist.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.todolist.R
import com.example.todolist.databinding.ActivityTaskViewBinding

class TaskViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskViewBinding
    private var titleFromActivity: String = ""
    private var descriptionFromActivity: String = ""
    private var idFromActivity: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initUI()
    }

    private fun init() {

        if (!this::binding.isInitialized) { throw Exception("Binding isn't initialised TaskFragment") }

        titleFromActivity = intent.getStringExtra("titleFromMainActivity").toString()
        descriptionFromActivity = intent.getStringExtra("descriptionFromMainActivity").toString()
        idFromActivity = intent.getLongExtra("idFromMainActivity",-1L)

        if (titleFromActivity != "" &&
            descriptionFromActivity != "" &&
            idFromActivity != -1L)
        {
            binding.editText1TaskViewActivity.setText(titleFromActivity)
            binding.editText2TaskViewActivity.setText(descriptionFromActivity)
        }else{
            throw Exception("data wasn't sent to TaskFragment")
        }

            if (binding.editText1TaskViewActivity.text.toString() != "" && binding.editText1TaskViewActivity.text.toString() != "") {
                markButtonEnabled(binding.view1TaskViewActivity)
            } else {
                markButtonDisable(binding.view1TaskViewActivity)
            }

            binding.editText1TaskViewActivity.afterTextChanged {
                if (binding.editText2TaskViewActivity.text.toString() != "" && binding.editText1TaskViewActivity.text.toString() != "") {
                    markButtonEnabled(binding.view1TaskViewActivity)
                } else {
                    markButtonDisable(binding.view1TaskViewActivity)
                }
            }

            binding.editText2TaskViewActivity.afterTextChanged {
                if (binding.editText2TaskViewActivity.text.toString() != "" && binding.editText1TaskViewActivity.text.toString() != "") {
                    markButtonEnabled(binding.view1TaskViewActivity)
                } else {
                    markButtonDisable(binding.view1TaskViewActivity)
                }
            }
    }

    private fun initUI() {
        binding.view1TaskViewActivity.setOnClickListener {
            val intent = intent
            intent.putExtra("titleFromTaskViewActivity", binding.editText1TaskViewActivity.text.toString())
            intent.putExtra("descriptionFromTaskViewActivity", binding.editText2TaskViewActivity.text.toString())
            intent.putExtra("idFromTaskViewActivity", idFromActivity)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        titleFromActivity = ""
        descriptionFromActivity = ""
        idFromActivity = -1L
    }
    private fun markButtonDisable(view: View) {
        view.isEnabled = false
        view.background.setTint(
            ContextCompat.getColor(
                view.context,
                R.color.color_background_button_disabled_task_view_activity
            )
        )
    }

    private fun markButtonEnabled(view: View) {
        view.isEnabled = true
        view.background.setTint(
            ContextCompat.getColor(
                view.context,
                R.color.color_background_button_enabled_task_view_activity
            )
        )
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

}