package com.example.testcoursach.model

import com.google.firebase.Timestamp

data class Answer (
    var answers: MutableMap<String, Int>,
    var RT: Long,
    var LT: Long,
    var date: Timestamp,
    var uid: String
)