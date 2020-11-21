package com.example.testcoursach

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.testing_questions.*


class TestingQuestions : AppCompatActivity() {


    private val TAG = "MyLogger"
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        db.collection("questions").get()
            .addOnSuccessListener { result ->
                for(document in result){
                    if (document.id == "question1") question_text!!.text = document.getString("question")
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testing_questions)

    }



}