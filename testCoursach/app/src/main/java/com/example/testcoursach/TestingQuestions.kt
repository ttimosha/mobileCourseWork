package com.example.testcoursach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

var previousQuestion: Button? = null
var backToStart: Button? = null
var option1: Button? = null
var option2: Button? = null
var option3: Button? = null
var option4: Button? = null
var timer: TextView? = null
var questionNumber: TextView? = null
var questionText: TextView? = null

class TestingQuestions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        previousQuestion = findViewById(R.id.previous_button)
        backToStart = findViewById(R.id.back_button)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        timer = findViewById(R.id.timer)
        questionNumber = findViewById(R.id.question_number)
        questionText = findViewById(R.id.question_text)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testing_questions)
    }
}