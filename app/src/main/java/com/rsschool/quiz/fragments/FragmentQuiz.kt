package com.rsschool.quiz.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.Themes
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.interfaces.FragmentListener
import com.rsschool.quiz.interfaces.OnBackPressedListener
import com.rsschool.quiz.questions.ListQuestions
import com.rsschool.quiz.questions.Question

class FragmentQuiz : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private lateinit var fragmentListener: FragmentListener
    private lateinit var onBackPressedListener: OnBackPressedListener

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
    ): View {
//        change theme fragment

        val window = activity?.window
        window?.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.deep_orange_100_dark)

        context?.theme?.applyStyle(R.style.Theme_Quiz_First, true)

        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listQuestion = ListQuestions.listQuestions
        val toolbar = binding.toolbar
        val radioGroup = binding.radioGroup
        val nextButton = binding.nextButton
        val previousButton = binding.previousButton

        var score = arguments?.get(SCORE) as Int
        var position = arguments?.get(POSITION) as Int


        if (position < listQuestion.size) {
            when {
                listQuestion[position].id == 1 -> {
                    toolbar.navigationIcon = null
                    nextButton.isEnabled = false
                    previousButton.isEnabled = false
                }
                listQuestion[position] == listQuestion.last() -> {
                    nextButton.text = getString(R.string.result_btn)
                }
            }

            toolbar.title = "Question ${listQuestion[position].id}"
            binding.question.text = listQuestion[position].question
            for (i in 0 until radioGroup.childCount) {
                val radioButton: View = radioGroup.getChildAt(i)
                if (radioButton is RadioButton) {
                    radioButton.text = listQuestion[position].options[i]
                }
            }

            nextButton.isEnabled = false

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                nextButton.isEnabled = true

                val checkBtn: RadioButton = radioGroup.findViewById(checkedId)
                val text = checkBtn.text.toString()

                if (text == listQuestion[position].correctAnswer) {
                    score += 1
                }
            }

            nextButton.setOnClickListener {
                position += 1
                if (position < listQuestion.size) {
                    fragmentListener.second(
                        newInstance(
                            position,
                            score
                        )
                    )
                } else {
                    fragmentListener.second(
                        FragmentResult.newInstance(score)
                    )
                }
            }
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedListener.onBackPressed()
        }

        previousButton.setOnClickListener {
            onBackPressedListener.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(position: Int, score: Int): FragmentQuiz {
            val fragment = FragmentQuiz()
            fragment.arguments = Bundle().apply {
                putInt(POSITION, position)
//                putString(QUESTION, question.toString())
                putInt(SCORE, score)
            }
            return fragment
        }

        private const val QUESTION = "QUESTION"
        private const val POSITION = "POSITION"
        private const val SCORE = "SCORE"
    }
}