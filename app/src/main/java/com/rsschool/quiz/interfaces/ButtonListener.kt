package com.rsschool.quiz.interfaces

import java.io.Serializable

interface ButtonListener {
    fun shareResult(score: Int, userAnswer: ArrayList<String>)
    fun restart()
    fun closeApp()
}