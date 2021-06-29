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
import kotlin.random.Random

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
//        change fragment theme
        val theme = Themes.values()
        val i = arguments?.get(POSITION) as Int

        if(i < theme.size){
            context?.theme?.applyStyle(theme[i].theme, true)
            val window = activity?.window
            window?.statusBarColor = ContextCompat.getColor(requireActivity(), theme[i].color)
        } else {
            val j = Random.nextInt(theme.size)
            context?.theme?.applyStyle(theme[j].theme, true)
            val window = activity?.window
            window?.statusBarColor = ContextCompat.getColor(requireActivity(), theme[j].color)
        }

//        add binding
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
        val userAnswer = arguments?.getStringArrayList(USER_OPTION) as ArrayList<String>
//        val userCheckedId = arguments?.get(USER_OPTION_ID) as Int


        if (position < listQuestion.size) {
//            hide buttons of the first fragment
            when {
                listQuestion[position].id == 1 -> {
                    toolbar.navigationIcon = null
                    previousButton.isEnabled = false
                }
//            change next button of the last fragment
                listQuestion[position] == listQuestion.last() -> {
                    nextButton.text = getString(R.string.result_btn)
                }
            }

//            add values
            toolbar.title = "Question ${listQuestion[position].id}"
            binding.question.text = listQuestion[position].question
            for (i in 0 until radioGroup.childCount) {
                val radioButton: View = radioGroup.getChildAt(i)
                if (radioButton is RadioButton) {
                    radioButton.text = listQuestion[position].options[i]
                }
            }

//            hide next button
            nextButton.isEnabled = false

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
//                activate next button
                nextButton.isEnabled = true

//                check clicked RadioButton
                val checkBtn: RadioButton = radioGroup.findViewById(checkedId)
                val text = checkBtn.text.toString()
                userAnswer.add(text)

//                check if the answer is correct
                if (text == listQuestion[position].correctAnswer) {
                    score += 1
                }

//                if (userCheckedId != 0){
//                    (radioGroup.findViewById(userCheckedId) as RadioButton).isChecked = true
//                }
            }

            nextButton.setOnClickListener {
                position += 1
                if (position < listQuestion.size) {
                    fragmentListener.second(
                        newInstance(
                            position,
                            score,
                            radioGroup.checkedRadioButtonId,
                            userAnswer
                        )
                    )
                } else {
                    fragmentListener.second(
                        FragmentResult.newInstance(score, userAnswer)
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
        fun newInstance(position: Int, score: Int, userOptionId: Int, userOption:ArrayList<String>): FragmentQuiz {
            val fragment = FragmentQuiz()
            fragment.arguments = Bundle().apply {
                putInt(POSITION, position)
                putInt(SCORE, score)
                putInt(USER_OPTION_ID, userOptionId)
                putStringArrayList(USER_OPTION, userOption)
            }
            return fragment
        }

        private const val POSITION = "POSITION"
        private const val SCORE = "SCORE"
        private const val USER_OPTION_ID = "USER_OPTION_ID"
        private const val USER_OPTION = "USER_OPTION"
    }
}