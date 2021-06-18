package com.rsschool.quiz.fragments

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.interfaces.FragmentListener
import com.rsschool.quiz.questions.ListQuestions
import com.rsschool.quiz.questions.Question


class FragmentFirst : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentListener: FragmentListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentListener = context as FragmentListener
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
        val position = 0

        binding.toolbar.title = "Question ${listQuestion[position].id}"

        binding.question.text = listQuestion[position].question

        for (i in 0 until radioGroup.childCount) {
            val radioButton: View = radioGroup.getChildAt(i)
            if (radioButton is RadioButton) {
                radioButton.text = listQuestion[position].options[i]
            }
        }

/*        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            
        }*/

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.option_four) {
                //TODO
            }

            binding.nextButton.setOnClickListener {
                if (id!=-1) {
                    fragmentListener.second()
                } else {
                    Toast.makeText(activity, "Nothing selected", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.previousButton.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(id: Int, question: Question, themeQuizFirst: Int): FragmentFirst {
            val fragment = FragmentFirst()
            val args = Bundle()
//            args.putInt(ID, id)
//            args.putString(QUESTION, question.toString())
//            args.putInt(THEME, theme)
            fragment.arguments = args
            return fragment
        }

        private const val ID = "ID"
        private const val QUESTION = "QUESTION"
        private const val THEME = "THEME"
    }
}