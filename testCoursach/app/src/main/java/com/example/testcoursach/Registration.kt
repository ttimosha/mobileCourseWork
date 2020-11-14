package com.example.testcoursach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

var register: Button? = null
var back: Button? = null
var username: TextView? = null
var email: TextView? = null
var password: TextView? = null
var passwordConfirm: TextView? = null

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        register = findViewById(R.id.register_button)
        back = findViewById(R.id.back_button)
        username = findViewById(R.id.username_input)
        email = findViewById(R.id.email_input)
        password = findViewById(R.id.password_input)
        passwordConfirm = findViewById(R.id.confirm_password)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
    }
}