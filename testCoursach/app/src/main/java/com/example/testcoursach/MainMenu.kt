package com.example.testcoursach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

var reg: Button? = null
var login: Button? = null
var startTest: Button? = null

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        reg = findViewById(R.id.register_button)
        login = findViewById(R.id.login_button)
        startTest = findViewById(R.id.test_button)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
    }
}