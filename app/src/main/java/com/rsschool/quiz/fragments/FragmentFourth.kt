package com.rsschool.quiz.fragments

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.interfaces.FragmentListener
import com.rsschool.quiz.interfaces.OnBackPressedListener
import com.rsschool.quiz.questions.ListQuestions
import com.rsschool.quiz.questions.Question

class FragmentFourth : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBackPressedListener: OnBackPressedListener
    private lateinit var fragmentListener: FragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentListener = context as FragmentListener
            onBackPressedListener = context as OnBackPressedListener
        } catch (e: Exception) {
            throw RuntimeException("$context must implement QuizFragmentListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioGroup = binding.radioGroup
        val listQuestion = ListQuestions.listQuestions

        val position = 3

            binding.toolbar.title = "Question ${listQuestion[position].id}"

            binding.question.text = listQuestion[position].question

        for (i in 0 until radioGroup.childCount) {
            val radioButton: View = radioGroup.getChildAt(i)
            if (radioButton is RadioButton) {
                radioButton.text = listQuestion[position].options[i]
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.nextButton.setOnClickListener {
                fragmentListener.fives()
            }
        }

        binding.previousButton.setOnClickListener {
            onBackPressedListener.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance(id: Int, question: Question, themeQuizFirst: Int): FragmentFourth {
            val fragment = FragmentFourth()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        private const val SCORE = "SCORE"
        private const val THEME = "THEME"
    }
/*    companion object {
        @JvmStatic
        fun newInstance(score: String, theme:Int): FragmentSecond {
            val fragment = FragmentSecond()
            val args = Bundle()
            args.putString(SCORE, score)
            args.putInt(THEME, theme)
            fragment.arguments = args
            return fragment
        }

        private const val SCORE = "SCORE"
        private const val THEME = "THEME"
    }*/
}