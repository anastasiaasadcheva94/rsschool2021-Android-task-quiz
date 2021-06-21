package com.rsschool.quiz.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.interfaces.ButtonListener
import com.rsschool.quiz.interfaces.FragmentListener
import com.rsschool.quiz.interfaces.OnBackPressedListener
import com.rsschool.quiz.questions.ListQuestions
import com.rsschool.quiz.questions.Question

class FragmentResult : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var buttonListener: ButtonListener
    private lateinit var fragmentListener: FragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentListener = context as FragmentListener
            buttonListener = context as ButtonListener
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

        window?.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.deep_orange_100_dark)

        context?.theme?.applyStyle(R.style.Theme_Quiz_First, true)

        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listQuestion = ListQuestions.listQuestions

        val count = arguments?.get(SCORE) as Int
        val score = (count.toDouble() / listQuestion.size) * 100

        binding.result.text = "Your result ${score.toInt()}%"

        binding.shareBtn.setOnClickListener {
            buttonListener.shareResult()
        }

        binding.backBtn.setOnClickListener {
            buttonListener.restart()
        }

        binding.closeBtn.setOnClickListener {
            buttonListener.closeApp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        fun newInstance(score: Int): FragmentResult {
            val fragment = FragmentResult()
            fragment.arguments = Bundle().apply{
                val option = ""
                putString(OPTION, option)
                putInt(SCORE, score)
            }
            return fragment
        }

        private const val OPTION = "OPTION"
        private const val SCORE = "SCORE"
    }
}