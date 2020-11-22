package com.example.testcoursach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class TestingMenu : AppCompatActivity() {
    var startTestButton: Button? = null
    var backToMenu: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        startTestButton = findViewById(R.id.startTest_button)
        backToMenu = findViewById(R.id.back_button)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testing_menu)
    }
    fun onClickTest(view: View?) {
        startActivity(Intent(this, TestingQuestions::class.java))
    }

    fun onClickBack(view: View?) {
        startActivity(Intent(this, MainMenu::class.java))
    }
}