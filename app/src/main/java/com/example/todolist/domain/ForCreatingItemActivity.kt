package com.example.todolist.domain

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.todolist.R

class ForCreatingItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)
        initUI()
    }

    private fun initUI(){
        var importance : Int = resources.getColor(R.color.gray_item)
        var count = 1
        findViewById<View>(R.id.activity_3_button_1).setOnClickListener{
            setResult(RESULT_CANCELED,intent)
            finish() }

        findViewById<View>(R.id.activity_3_textView_1).setOnClickListener{
            val view : View = findViewById(R.id.activity_3_textView_1)
            if (count == 0){
                importance = resources.getColor(R.color.gray_item)
                view.background.setTint(importance)
                count++
            }else {
                if (count == 1) {
                    importance = resources.getColor(R.color.green1)
                    view.background.setTint(importance)
                    count++
                }else {
                    if (count == 2) {
                        importance = resources.getColor(R.color.yellow1)
                        view.background.setTint(importance)
                        count++
                    }else {
                        importance = getResources().getColor(R.color.red1)
                        view.background.setTint(importance)
                        count = 0
                    }
                }
            }
        }

        findViewById<View>(R.id.activity_3_button_2).setOnClickListener{
            val title : EditText = findViewById(R.id.activity_3_editView_1)
            val description : EditText = findViewById(R.id.activity_3_editView_2)


            if( title.text.toString() != "" && description.text.toString() != ""){
                intent.putExtra("title",title.text.toString())
                intent.putExtra("description",description.text.toString())
                intent.putExtra("importance",importance.toInt())
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

