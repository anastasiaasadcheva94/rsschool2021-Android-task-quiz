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
    private val listQuestion = ListQuestions.listQuestions
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.Theme_Quiz_Second)//Меняем тему активити вызывать здесь метод смены темы when
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFirstFragment()
    }

    private fun openFirstFragment() {
        val startQuiz: Fragment = FragmentFirst.newInstance(
            listQuestion[position].id,
            listQuestion[position],
            R.style.Theme_Quiz_First
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, startQuiz)
            .addToBackStack(null)
            .commit()
    }

    private fun openSecondFragment() {
        val secondFragment: Fragment = FragmentSecond.newInstance(
            listQuestion[position].id,
            listQuestion[position],
            R.style.Theme_Quiz_Second
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, secondFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openThirdFragment() {
        val secondFragment: Fragment = FragmentThird.newInstance(
            listQuestion[position].id,
            listQuestion[position],
            R.style.Theme_Quiz_Third
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, secondFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openFourthFragment() {
        val secondFragment: Fragment = FragmentFourth.newInstance(
            listQuestion[position].id,
            listQuestion[position],
            R.style.Theme_Quiz_Fourth
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, secondFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openFivesFragment() {
        val secondFragment: Fragment = FragmentFives.newInstance(
            listQuestion[position].id,
            listQuestion[position],
            R.style.Theme_Quiz_Fives
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, secondFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openResultFragment() {
        val resultFragment: Fragment = FragmentResult.newInstance(
            listQuestion[position].id,
            listQuestion[position],
            R.style.Theme_Quiz_First
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, resultFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun second() {
        openSecondFragment()
    }

    override fun third() {
        openThirdFragment()
    }

    override fun fourth() {
        openFourthFragment()
    }

    override fun fives() {
        openFivesFragment()
    }

    override fun result() {
        openResultFragment()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            this.finish()
        }
    }

    override fun shareResult() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = "Here is the share content body (result test)"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Quiz result")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share via :"))
    }

    override fun restart() {
        finish()
        startActivity(intent)
    }

    override fun closeApp() {
        finish()
        exitProcess(0)
    }
}