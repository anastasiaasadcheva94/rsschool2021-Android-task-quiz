package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.fragments.*
import com.rsschool.quiz.interfaces.ButtonListener
import com.rsschool.quiz.interfaces.FragmentListener
import com.rsschool.quiz.interfaces.OnBackPressedListener
import com.rsschool.quiz.questions.ListQuestions
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), FragmentListener, OnBackPressedListener, ButtonListener {
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFirstFragment()
    }

    private fun openFirstFragment() {
        val startQuiz: Fragment = FragmentQuiz.newInstance(
            position,
            0,
            0,
            arrayListOf()
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, startQuiz)
            .addToBackStack(null)
            .commit()
    }

    private fun openSecondFragment(nextFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun second(nextFragment: Fragment) {
        openSecondFragment(nextFragment)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            this.finish()
        }
    }

    override fun restart() {
        val startQuiz: Fragment = FragmentQuiz.newInstance(
            position,
            0,
            0,
            arrayListOf()
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, startQuiz)
            .commit()
    }

    override fun closeApp() {
        finish()
        exitProcess(0)
    }

    override fun shareResult(score: Int, userAnswer:ArrayList<String>) {
        var i = 0
        fun getMessage(score:Int): String {
            return with(StringBuilder()) {
                appendLine("Your result: ${score}%")
                appendLine()
                while (i < ListQuestions.listQuestions.size){
                    appendLine("${ListQuestions.listQuestions[i].id}) ${ListQuestions.listQuestions[i].question}")
                    appendLine("\n")
                    appendLine("Your answer: ${userAnswer[i]}")
                    appendLine("\n\n")
                    i++
                }
                toString()
            }
        }

        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
        sharingIntent.putExtra(
            Intent.EXTRA_TEXT,
            getMessage(score)
        )
        startActivity(Intent.createChooser(sharingIntent, "Share via :"))
    }
}