package com.example.testcoursach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

var loginButton: Button? = null
var backButton: Button? = null
var usernameInput: TextView? = null
var emailInput: TextView? = null
class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        loginButton = findViewById(R.id.login_button)
        backButton = findViewById(R.id.back_button)
        usernameInput = findViewById(R.id.username_input)
        emailInput = findViewById(R.id.email_input)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
    }
}