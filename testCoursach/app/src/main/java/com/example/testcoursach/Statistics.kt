package com.example.testcoursach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testcoursach.adapter.Adapter
import com.example.testcoursach.model.Answer
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {

    private val TAG = "MyLogger"
    private val db = Firebase.firestore
    private var user: FirebaseUser? = null
    private var answersList: MutableList<Answer> = mutableListOf()
    private var idAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        user = Firebase.auth.currentUser
        if (user!=null) {
            if (user!!.uid=="90uVJp2BwChx82xwl5DUTEbQvXe2")  {
                db.collection("answers").get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                Log.d(TAG, "${document.id} => ${document.data}")
                                answersList.add(Answer(document.get("answers") as MutableMap<String, Int>, document.get("RT") as Long, document.get("LT") as Long, document.get("date") as Timestamp, document.get("uid") as String))
                            }
                            recycler_view.adapter = Adapter(this, answersList)
                        }
                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error getting documents: ", exception)
                        }
            }
            else {
                db.collection("answers")
                        .whereEqualTo("uid", user!!.uid)
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                Log.d(TAG, "${document.id} => ${document.data}")
                                answersList.add(Answer(document.get("answers") as MutableMap<String, Int>, document.get("RT") as Long, document.get("LT") as Long, document.get("date") as Timestamp, ""))
                            }
                            recycler_view.adapter = Adapter(this, answersList)
                        }
                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error getting documents: ", exception)
                        }
            }
        }
        back_button2.setOnClickListener {
            finish()
        }
    }

}