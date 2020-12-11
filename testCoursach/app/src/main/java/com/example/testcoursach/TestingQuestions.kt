package com.example.testcoursach

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.testing_questions.*
import kotlin.properties.Delegates


class TestingQuestions : AppCompatActivity() {

    private val TAG = "MyLogger"
    private val db = Firebase.firestore
    private val rt_1 = listOf(3, 4, 6, 7, 9, 12, 13, 14, 17, 18)
    private var sum_rt_1 = 0
    private val  rt_2 = listOf(1, 2, 5, 8, 10, 11, 15, 16, 19, 20)
    private var sum_rt_2 = 0
    private val lt_1 = listOf(22, 23, 24, 25, 28, 29, 31, 32, 34, 37, 38, 40)
    private var sum_lt_1 = 0
    private val lt_2 = listOf(21, 26, 27, 30, 33, 35, 36, 39)
    private var sum_lt_2 = 0
    private var currentQ = 1
    private var currentP = 0
    private var RT = 0
    private var LT = 0
    private var user: FirebaseUser? = null
    private var answersMap: MutableMap<String?, Int> = mutableMapOf()
    private var questionsList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testing_questions)
        user = Firebase.auth.currentUser
        previous_button.visibility = Button.INVISIBLE
        db.collection("questions").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        questionsList.add(document.getString("question")!!)
                    }
                    question_text!!.text = questionsList[0]
                    updateUI(true)
                    chronometer.start()
                }
                .addOnFailureListener{ exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
    }

    @SuppressLint("SetTextI18n")
    fun onClickAns(view: View?) {
        var button: Button = view as Button
        val points = checkPoints(button.id)
        if (rt_1.contains(currentQ)) sum_rt_1+=points
        else if (rt_2.contains(currentQ)) sum_rt_2+=points
        else if (lt_1.contains(currentQ)) sum_lt_1+=points
        else if (lt_2.contains(currentQ)) sum_lt_2+=points
        currentP = points
        answersMap.plusAssign(questionsList[currentQ-1] to currentP)

        if (currentQ !=40) {
            currentQ+= 1
            updateUI(true)
            question_number.text = "Вопрос $currentQ из 40"
            question_text!!.text = questionsList[currentQ-1]
            return
        } else {
            updateUI(false)
            currentQ+=1
            RT = sum_rt_1 - sum_rt_2 + 35
            LT = sum_lt_1 - sum_lt_2 + 35
            result.text = "Ситуативная тревожность RT = $RT \nЛичностная тревожность LT = $LT \n\nУровень тревожности до 30 баллов считается низким, от 30 до 45 баллов - умеренным, " +
                    "от 46 баллов и выше - высоким. Минимальная оценка по каждой шкале - 20 баллов, максимальная - 80 баллов."
            return
        }
    }

    @SuppressLint("SetTextI18n")
    fun onClickPrevious (view: View?) {
        currentQ-=1
        updateUI(true)
        question_number.text = "Вопрос $currentQ из 40"
        question_text!!.text = questionsList[currentQ-1]
        when (answersMap[questionsList[currentQ-1]]) {
            1 -> option1.text = option1.text.toString() + "\n(предыдущий ответ)"
            2 -> option2.text = option2.text.toString() + "\n(предыдущий ответ)"
            3 -> option3.text = option3.text.toString() + "\n(предыдущий ответ)"
            4 -> option4.text = option4.text.toString() + "\n(предыдущий ответ)"
        }

        if (rt_1.contains(currentQ)) sum_rt_1-=currentP
        else if (rt_2.contains(currentQ)) sum_rt_2-=currentP
        else if (lt_1.contains(currentQ)) sum_lt_1-=currentP
        else if (lt_2.contains(currentQ)) sum_lt_2-=currentP
        return
    }

    fun onClickBack(view: View?) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Вы уверены, что хотите покинуть тест?")
                .setCancelable(false)
                .setPositiveButton("Да") { dialog, id ->
                    startActivity(Intent(this, MainMenu::class.java))
                }
                .setNegativeButton("Нет") { dialog, id ->
                    dialog.dismiss()
                }
        val alert = builder.create()
        alert.show()
    }

    fun onClickRestart(view: View?) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Вы уверены, что хотите перепройти тест? Результаты данного прохождения не сохранятся.")
                .setCancelable(false)
                .setPositiveButton("Да") { dialog, id ->
                    finish()
                    startActivity(intent)
                }
                .setNegativeButton("Нет") { dialog, id ->
                    dialog.dismiss()
                }
        val alert = builder.create()
        alert.show()

    }

    fun onClickSave(view: View?) {
        if (user != null) {
            val data = hashMapOf(
                    "answers" to answersMap,
                    "RT" to RT,
                    "LT" to LT,
                    "uid" to user!!.uid,
                    "date" to Timestamp.now()
            )

            db.collection("answers")
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Успешно сохранено")
                                .setCancelable(false)
                                .setPositiveButton("ОК") { dialog, id ->
                                    finish()
                                }
                        val alert = builder.create()
                        alert.show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
        }
        else finish()
    }

    private fun checkPoints(buttonId: Int): Int {
        when(buttonId) {
            R.id.option1 -> return 1
            R.id.option2 -> return 2
            R.id.option3 -> return 3
            R.id.option4 -> return 4
        }
        return 0
    }

    private fun updateUI(visibility: Boolean) {
        if (visibility) {
            result.text = ""
            option1.visibility = Button.VISIBLE
            option1.text = "Нет, это не так"
            option2.visibility = Button.VISIBLE
            option2.text = "Пожалуй так"
            option3.visibility = Button.VISIBLE
            option3.text = "Верно"
            option4.visibility = Button.VISIBLE
            option4.text = "Совершенно верно"
            restart.visibility = Button.INVISIBLE
            save.visibility = Button.INVISIBLE
            if (currentQ == 1)
                previous_button.visibility = Button.INVISIBLE
            else previous_button.visibility = Button.VISIBLE

        } else {
            question_text.text = ""
            question_number.text = ""
            option1.visibility = Button.INVISIBLE
            option2.visibility = Button.INVISIBLE
            option3.visibility = Button.INVISIBLE
            option4.visibility = Button.INVISIBLE
            restart.visibility = Button.VISIBLE
            save.visibility = Button.VISIBLE
            if (user==null) textView10.visibility = TextView.VISIBLE
        }
    }
}