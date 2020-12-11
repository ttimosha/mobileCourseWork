package com.example.testcoursach.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.testcoursach.AnswerInformation
import com.example.testcoursach.R
import com.example.testcoursach.model.Answer
import kotlinx.android.synthetic.main.testing_questions.*
import java.text.SimpleDateFormat

class ResultAdapter(private val context: Context,
              private val map: MutableMap<String, Long>
) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val largeText: TextView = view.findViewById(R.id.textViewLarge)
        val smallText: TextView = view.findViewById(R.id.textViewSmall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.answer_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return map.count()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keys = map.keys.toList()
        val answer = map[keys[position]]
        holder.smallText.text = "Утверджение: ${keys[position]}"
        holder.largeText.text = "Ваш ответ: ${getAnswStr(answer)}"
    }

    private fun getAnswStr(long: Long?): String {
            when (long!!.toInt()) {
                1 -> return "Нет, это не так"
                2 -> return "Пожалуй так"
                3 -> return "Верно"
                4 -> return "Совершенно верно"
                else -> return ""
        }
    }
}