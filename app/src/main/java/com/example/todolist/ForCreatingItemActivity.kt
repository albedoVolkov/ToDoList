package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class ForCreatingItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_creating_item)
        initUI()
    }

    private fun initUI(){
        findViewById<View>(R.id.activity_2_Button_Exit).setOnClickListener{
            setResult(RESULT_OK,intent)
            finish() }
        findViewById<View>(R.id.activity_2_Button_Check).setOnClickListener{
            val title : EditText = findViewById<EditText>(R.id.activity_2_Title)
            val description : EditText = findViewById<EditText>(R.id.activity_2_Description)
            intent.putExtra("title",title.text.toString())
            intent.putExtra("description",description.text.toString())
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}

