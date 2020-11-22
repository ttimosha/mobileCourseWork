package com.example.testcoursach

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main_menu.*
import kotlinx.android.synthetic.main.registration.*
import kotlinx.android.synthetic.main.registration.register_button


class Registration : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private val TAG = "MyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
        if (auth!!.currentUser != null) {
            // User is signed in
            Firebase.auth.signOut()
        } else {
            // No user is signed in
        }
    }

    fun onClickRegistration (view: View?) {
        if (email?.text.toString().isEmpty()) {
            email?.error = "Пожалуйста, введите свою почту"
            email?.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email?.text.toString()).matches()) {
            email?.error = "Пожалуйста, введите корректную почту"
            email?.requestFocus()
            return
        }

        if (password?.text.toString().isEmpty()) {
            password?.error = "Пожалуйста, введите пароль"
            password?.requestFocus()
            return
        }

        if (password?.text!!.length < 6) {
            password?.error = "Введите не менее 6 символов"
            password?.requestFocus()
            return
        }

        if (password_confirm.text.toString() != password.text.toString()) {
            password_confirm?.error = "Пароли не совпадают"
            password_confirm?.requestFocus()
            return
        }


        auth?.createUserWithEmailAndPassword(email?.text.toString(), password?.text.toString())
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, Login::class.java))
                    finish()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Регистрация не удалась. Попробуйте ещё раз",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun onClickBack(view: View?) {
        startActivity(Intent(this, MainMenu::class.java))
    }
}