package com.rsschool.quiz.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.interfaces.FragmentListener
import com.rsschool.quiz.interfaces.OnBackPressedListener
import com.rsschool.quiz.questions.ListQuestions
import com.rsschool.quiz.questions.Question

class FragmentFives : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBackPressedListener: OnBackPressedListener
    private lateinit var fragmentListener: FragmentListener
    private var score: Int = 0

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
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.deep_purple_100_dark)

        context?.theme?.applyStyle(R.style.Theme_Quiz_Fives, true)

        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioGroup = binding.radioGroup
        val listQuestion = ListQuestions.listQuestions

        val position = 4

        binding.toolbar.title = "Question ${listQuestion[position].id}"

        binding.question.text = listQuestion[position].question

        for (i in 0 until radioGroup.childCount) {
            val radioButton: View = radioGroup.getChildAt(i)
            if (radioButton is RadioButton) {
                radioButton.text = listQuestion[position].options[i]
            }
        }

        binding.nextButton.text = "Result"
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.option_four) {
                score = 20
            }

            binding.nextButton.setOnClickListener {
                fragmentListener.second(FragmentResult.newInstance(listQuestion[position].id, listQuestion[position], score))
            }
        }

        binding.nextButton.setOnClickListener {
            Toast.makeText(activity, "Nothing selected", Toast.LENGTH_SHORT).show()
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
        fun newInstance(id: Int, question: Question, score:Int): FragmentFives {
            val fragment = FragmentFives()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        private const val SCORE = "SCORE"
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