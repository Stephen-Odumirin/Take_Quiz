package com.stdev.takequiz.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stdev.takequiz.R
import com.stdev.takequiz.data.model.Category
import com.stdev.takequiz.data.model.Question
import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import com.stdev.takequiz.databinding.FragmentQuizBinding
import com.stdev.takequiz.presentation.viewmodel.QuizViewModel
import com.stdev.takequiz.presentation.viewmodel.QuizViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuizFragment : Fragment(),View.OnClickListener{

    @Inject
    lateinit var factory: QuizViewModelFactory
    lateinit var viewModel : QuizViewModel
    private lateinit var binding : FragmentQuizBinding

    private lateinit var category: Category
    private var difficulty = "any"
    private var type = "multiple"
    private var questions = 10

    private lateinit var quiz : Quiz
    private lateinit var questionList : List<QuizResult>
    private var questionList2 : MutableList<Question> = mutableListOf()

    private var currentQuestion = 1
    private var correctAnswers = 0
    private var movetonext = false
    private var cananswer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = QuizFragmentArgs.fromBundle(requireArguments()).category
        difficulty = QuizFragmentArgs.fromBundle(requireArguments()).difficulty
        type = QuizFragmentArgs.fromBundle(requireArguments()).type
        questions = QuizFragmentArgs.fromBundle(requireArguments()).questions.toInt()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentQuizBinding.bind(view)
        viewModel = ViewModelProvider(this,factory)[QuizViewModel::class.java]

        binding.quizOptionOne.setOnClickListener(this)
        binding.quizOptionTwo.setOnClickListener(this)
        binding.quizOptionThree.setOnClickListener(this)
        binding.quizOptionFour.setOnClickListener(this)
        binding.quizNextButton.setOnClickListener(this)

        getQuizData()

    }

    private fun getQuizData() {
        viewModel.getQuiz(questions, category.id, difficulty, type)

        viewModel.quiz.observe(viewLifecycleOwner) {
            binding.quizProgressBar.visibility = View.VISIBLE
            binding.quizProgressText.visibility = View.VISIBLE
            Log.i("QuizFragment", "Observing quiz...")
            if (it.responseCode == 0) {
                binding.quizProgressBar.visibility = View.INVISIBLE
                binding.quizProgressText.visibility = View.INVISIBLE
                //the api response was gotten
                questionList = it.results
                //displayQuestion()
                convert()
                Log.i("QuizFragment", "${it.results.size}")
            } else {
                //the api response wasn't gotten
                binding.quizProgressBar.visibility = View.INVISIBLE
                binding.quizProgressText.visibility = View.INVISIBLE
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Error")
                    .setMessage("There aren't sufficient questions in the database. Try reducing the number of questions and try again. ): ")
                    .setCancelable(false)
                    .setPositiveButton("Okay") { _, _ ->
                        findNavController().navigate(R.id.action_quizFragment_to_homeFragment)
                    }
                    .show()
                Log.i("QuizFragment", "Error, ${it.responseCode}")
            }
        }
    }

    private fun convert(){

        for (i in questionList.indices){
            val question = Question("$i","null","null","null","null","null",0)
            questionList2.add(question)
        }

        for (i in questionList.indices){
            questionList2[i].id = i.toString()
            questionList2[i].question = questionList[i].question
            val answer = listOf(1,2,3,4).random()
            when(answer){
                1 -> {
                    questionList2[i].optionOne = questionList[i].correctAnswer
                    questionList2[i].optionTwo = questionList[i].incorrectAnswers[0]
                    questionList2[i].optionThree = questionList[i].incorrectAnswers[1]
                    questionList2[i].optionFour = questionList[i].incorrectAnswers[2]
                    questionList2[i].correctAnswer = 1
                }
                2 -> {
                    questionList2[i].optionOne = questionList[i].incorrectAnswers[0]
                    questionList2[i].optionTwo = questionList[i].correctAnswer
                    questionList2[i].optionThree = questionList[i].incorrectAnswers[1]
                    questionList2[i].optionFour = questionList[i].incorrectAnswers[2]
                    questionList2[i].correctAnswer = 2
                }
                3 -> {
                    questionList2[i].optionOne = questionList[i].incorrectAnswers[0]
                    questionList2[i].optionTwo = questionList[i].incorrectAnswers[1]
                    questionList2[i].optionThree = questionList[i].correctAnswer
                    questionList2[i].optionFour = questionList[i].incorrectAnswers[2]
                    questionList2[i].correctAnswer = 3
                }
                4 -> {
                    questionList2[i].optionOne = questionList[i].incorrectAnswers[0]
                    questionList2[i].optionTwo = questionList[i].incorrectAnswers[1]
                    questionList2[i].optionThree = questionList[i].incorrectAnswers[2]
                    questionList2[i].optionFour = questionList[i].correctAnswer
                    questionList2[i].correctAnswer = 4
                }
            }



        }

        Log.i("QuizFragment","$questionList2")

        displayQuestion()

    }

    private fun displayQuestion(){

        //this means the user must answer this question before he can proceed
        movetonext = false
        binding.quizNextButton.isEnabled = false

        //setting the data to the corresponding views
        binding.quizQuestionNo.text = "Question $currentQuestion/$questions"
        binding.quizQuestionText.text = questionList2[currentQuestion-1].question
        binding.quizOptionOne.text = questionList2[currentQuestion-1].optionOne
        binding.quizOptionTwo.text = questionList2[currentQuestion-1].optionTwo
        binding.quizOptionThree.text = questionList2[currentQuestion-1].optionThree
        binding.quizOptionFour.text = questionList2[currentQuestion-1].optionFour
        cananswer = true

        //changed the text on the button accordingly
        if (currentQuestion == questions){
            binding.quizNextButton.text = "Submit"
        }
        else{
            binding.quizNextButton.text = "Next"
        }


    }

    private fun verityAnswer(answerId : Int,textView: TextView){
        //this means the user can go to the next question only after answering the current one
        movetonext = true
        binding.quizNextButton.isEnabled = true
        if(cananswer){
            //if the answer id is question to the chosen textview
            if(answerId == questionList2[currentQuestion-1].correctAnswer){
                //user picked the correct answer
                correctAnswers++
                textView.background = ContextCompat.getDrawable(requireContext(),R.drawable.correct_answer)
            }else{
                //user picked the wrong answer
                textView.background = ContextCompat.getDrawable(requireContext(),R.drawable.wrong_answer)
                when(questionList2[currentQuestion-1].correctAnswer){
                    1 -> {binding.quizOptionOne.background = ContextCompat.getDrawable(requireContext(),R.drawable.correct_answer)}
                    2 -> {binding.quizOptionTwo.background = ContextCompat.getDrawable(requireContext(),R.drawable.correct_answer)}
                    3 -> {binding.quizOptionThree.background = ContextCompat.getDrawable(requireContext(),R.drawable.correct_answer)}
                    4 -> {binding.quizOptionFour.background = ContextCompat.getDrawable(requireContext(),R.drawable.correct_answer)}
                }
            }
        }
        cananswer = false

    }

    private fun clear(){
        //clearing the fields
        binding.quizOptionOne.background = ContextCompat.getDrawable(requireContext(),R.drawable.option_unselected)
        binding.quizOptionTwo.background = ContextCompat.getDrawable(requireContext(),R.drawable.option_unselected)
        binding.quizOptionThree.background = ContextCompat.getDrawable(requireContext(),R.drawable.option_unselected)
        binding.quizOptionFour.background = ContextCompat.getDrawable(requireContext(),R.drawable.option_unselected)
    }

    private fun next(){
        //if there are enough questions to show
        if(currentQuestion <= questionList2.size) {
            //increasing the current question number and showing the corresponding question and clearing the previous answers selected by the user
            if (currentQuestion != questions) {
                currentQuestion++
                clear()
                displayQuestion()
            } else {
                submit()
            }
        }

    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.quizNextButton.id -> {
                next()
            }
            binding.quizOptionOne.id -> {
                verityAnswer(1,binding.quizOptionOne)
            }
            binding.quizOptionTwo.id -> {
                verityAnswer(2,binding.quizOptionTwo)
            }
            binding.quizOptionThree.id -> {
                verityAnswer(3,binding.quizOptionThree)
            }
            binding.quizOptionFour.id -> {
                verityAnswer(4,binding.quizOptionFour)
            }
        }
    }

    private fun submit(){
        //submitting the result of the questions taken
        Toast.makeText(requireContext(),"Correct answers $correctAnswers",Toast.LENGTH_SHORT).show()
    }

}