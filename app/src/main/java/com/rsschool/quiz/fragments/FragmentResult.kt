package com.rsschool.quiz.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.interfaces.ButtonListener
import com.rsschool.quiz.interfaces.FragmentListener
import com.rsschool.quiz.interfaces.OnBackPressedListener
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
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var count = 0

        binding.result.text = "Your result ${count}%"

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
        @JvmStatic
        fun newInstance(id: Int, question: Question, themeQuizFirst: Int): FragmentResult {
            val fragment = FragmentResult()
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