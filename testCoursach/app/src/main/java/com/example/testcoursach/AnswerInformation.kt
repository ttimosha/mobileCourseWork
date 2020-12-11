package com.example.testcoursach

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testcoursach.adapter.ResultAdapter
import kotlinx.android.synthetic.main.activity_answer_information.*


class AnswerInformation : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_information)

        val datetime = intent.getStringExtra("datetime")
        val RT = intent.getLongExtra("RT", 0)
        val LT = intent.getLongExtra("LT", 0)
        val answers = intent.getSerializableExtra("answerMap") as MutableMap<String, Long>
        datetimeView.text = "Дата прохождения: $datetime"
        LT_RT.text = "Ситуативная тревожность RT =  $RT\n\nЛичностная тревожность LT = $LT"
        recycler_view.adapter = ResultAdapter(this, answers)

        back_button3.setOnClickListener {
            finish()
        }
    }
}

