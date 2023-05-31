package com.example.todolist.domain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.todolist.R

class ForCreatingItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_creating_item)
        initUI()
    }

    private fun initUI(){
        findViewById<View>(R.id.activity_2_Button_Exit).setOnClickListener{
            setResult(RESULT_CANCELED,intent)
            finish() }

        findViewById<View>(R.id.activity_2_Button_Check).setOnClickListener{
            val title : EditText = findViewById<EditText>(R.id.activity_2_Title)
            val description : EditText = findViewById<EditText>(R.id.activity_2_Description)

            if( title.text.toString() != "" && description.text.toString() != ""){
                intent.putExtra("title",title.text.toString())
                intent.putExtra("description",description.text.toString())
                ////////////////////
                title.setHintTextColor(resources.getColor(R.color.white_hint))
                title.hint = "Title"
                description.setHintTextColor(resources.getColor(R.color.white_hint))
                description.hint = "Start typing"
                setResult(RESULT_OK,intent)
                finish()
            }else{
                if( title.text.toString() == "" ){
                    title.setHintTextColor(resources.getColor(R.color.red_hint_incorrect))
                    title.hint = "fill in this field"
                }
                if( description.text.toString() == ""){
                    description.setHintTextColor(resources.getColor(R.color.red_hint_incorrect))
                    description.hint = "fill in this field"
                }
            }
        }
    }
}

