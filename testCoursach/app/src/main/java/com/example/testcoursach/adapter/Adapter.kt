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
import kotlinx.android.synthetic.main.answer_list.view.*
import java.text.SimpleDateFormat
import kotlin.collections.HashMap

class Adapter(private val context: Context,
              private val list: MutableList<Answer>
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val largeText: TextView = view.findViewById(R.id.textViewLarge)
        val smallText: TextView = view.findViewById(R.id.textViewSmall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.answer_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val answer = list[position]
        val df = SimpleDateFormat("dd/MM/yyyy HH:mm")
        var date = df.format(answer.date.toDate())
        holder.largeText.text = "Дата прохождения: $date"
        holder.smallText.text = "Ситуативная тревожность RT =  ${answer.RT}\nЛичностная тревожность LT = ${answer.LT}\n\nНажмите для подробной информации"

        holder.itemView.setOnClickListener {
            val map = answer.answers as HashMap
            val intent = Intent(context, AnswerInformation::class.java).apply {
                putExtra("answerMap", map)
                putExtra("LT", answer.LT)
                putExtra("RT", answer.RT)
                putExtra("datetime", date)
            }
            context.startActivity(intent)
        }
    }
}