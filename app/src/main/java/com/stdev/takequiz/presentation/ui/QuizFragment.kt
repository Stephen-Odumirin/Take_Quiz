package com.stdev.takequiz.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stdev.takequiz.R
import com.stdev.takequiz.data.model.Category
import com.stdev.takequiz.databinding.FragmentQuizBinding
import com.stdev.takequiz.presentation.viewmodel.QuizViewModel
import com.stdev.takequiz.presentation.viewmodel.QuizViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class QuizFragment : Fragment(),View.OnClickListener{

    @Inject
    lateinit var factory: QuizViewModelFactory
    lateinit var viewModel : QuizViewModel
    private lateinit var binding : FragmentQuizBinding

    private lateinit var category : Category
    private var difficulty = ""
    private var type = "multiple"
    private var questions = 10
    private var fromDb = false
    private var quizid = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fromDb = QuizFragmentArgs.fromBundle(requireArguments()).fromdb
        quizid = QuizFragmentArgs.fromBundle(requireArguments()).id ?: "0"
        category = QuizFragmentArgs.fromBundle(requireArguments()).category ?: Category(0,"")
        difficulty = QuizFragmentArgs.fromBundle(requireArguments()).difficulty ?: ""
        type = QuizFragmentArgs.fromBundle(requireArguments()).type ?: "multiple"
        questions = QuizFragmentArgs.fromBundle(requireArguments()).questions?.toInt() ?: 0


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentQuizBinding.bind(view)
        viewModel = ViewModelProvider(this, factory)[QuizViewModel::class.java]

        binding.quizOptionOne.setOnClickListener(this)
        binding.quizOptionTwo.setOnClickListener(this)
        binding.quizOptionThree.setOnClickListener(this)
        binding.quizOptionFour.setOnClickListener(this)
        binding.quizNextButton.setOnClickListener(this)

        viewModelShits()

    }

    private fun viewModelShits(){
        if (fromDb){
            viewModel.getQuizFromDb(quizid)
        }else{
            viewModel.status.observe(viewLifecycleOwner) {
                if (!it) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Error")
                        .setMessage("There aren't sufficient questions in the database. Try reducing the number of questions and try again. ): ")
                        .setCancelable(false)
                        .setPositiveButton("Okay") { _, _ ->
                            findNavController().navigate(R.id.action_quizFragment_to_homeFragment)
                        }
                        .show()
                }
            }
            viewModel.getQuiz(questions, category.id, difficulty, type)
        }


        viewModel.currentQuiz.observe(viewLifecycleOwner) {
            clear()
            binding.quizQuestionText.text = HtmlCompat.fromHtml(it.question,HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.quizOptionOne.text = HtmlCompat.fromHtml(it.optionOne,HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.quizOptionTwo.text = HtmlCompat.fromHtml(it.optionTwo,HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.quizOptionThree.text = HtmlCompat.fromHtml(it.optionThree,HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.quizOptionFour.text = HtmlCompat.fromHtml(it.optionFour,HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        viewModel.currentQuestion.observe(viewLifecycleOwner) {
            binding.quizQuestionNo.text = "Question $it/${viewModel.questionList2.value?.size}"
        }

        viewModel.buttonText.observe(viewLifecycleOwner) {
            binding.quizNextButton.text = it
        }

        viewModel.movetonext.observe(viewLifecycleOwner) {
            binding.quizNextButton.isEnabled = it
        }

        viewModel.quizType.observe(viewLifecycleOwner){
            if (!it){
                binding.quizOptionThree.visibility = View.INVISIBLE
                binding.quizOptionFour.visibility = View.INVISIBLE
            }
        }
    }

    private fun clear(){
        //clearing the fields
        binding.quizOptionOne.background = ContextCompat.getDrawable(requireContext(),R.drawable.option_unselected)
        binding.quizOptionTwo.background = ContextCompat.getDrawable(requireContext(),R.drawable.option_unselected)
        binding.quizOptionThree.background = ContextCompat.getDrawable(requireContext(),R.drawable.option_unselected)
        binding.quizOptionFour.background = ContextCompat.getDrawable(requireContext(),R.drawable.option_unselected)
    }

    private fun showResult(){
        val date: Date = Calendar.getInstance().time
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
        val strDate: String = dateFormat.format(date)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Your Result")
            .setMessage("You answered ${viewModel.correctAnswers.value} question(s) correctly out of ${viewModel.questionList2.value?.size} questions. \nKeep it up")
            .setPositiveButton("Exit"){_,_->
                findNavController().navigateUp()
            }.setNegativeButton("Try Again"){_,_->
                viewModel.startAgain()
                viewModelShits()
            }
            .setNeutralButton("Save Quiz"){_,_->
                //lifecycleScope.launch(IO) {
                if (fromDb){
                    findNavController().navigateUp()
                }else{
                    viewModel.saveQuiz(category.name)
                    findNavController().navigateUp()
                }
            }
            .setCancelable(false)
            .show()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.quizNextButton.id -> {
                if (viewModel.currentQuestion.value == viewModel.questionList2.value?.size){
                    viewModel.endQuiz()
                    showResult()
                    //Toast.makeText(requireContext(),"Correct answers ${viewModel.correctAnswers.value}",Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.nextQuestion()
                }
            }
            binding.quizOptionOne.id -> {
                viewModel.verityAnswer(1,binding.quizOptionOne,requireContext(),binding.quizOptionOne,binding.quizOptionTwo,binding.quizOptionThree,binding.quizOptionFour)
            }
            binding.quizOptionTwo.id -> {
                viewModel.verityAnswer(2,binding.quizOptionTwo,requireContext(),binding.quizOptionOne,binding.quizOptionTwo,binding.quizOptionThree,binding.quizOptionFour)
            }
            binding.quizOptionThree.id -> {
                viewModel.verityAnswer(3,binding.quizOptionThree,requireContext(),binding.quizOptionOne,binding.quizOptionTwo,binding.quizOptionThree,binding.quizOptionFour)

            }
            binding.quizOptionFour.id -> {
                viewModel.verityAnswer(4,binding.quizOptionFour,requireContext(),binding.quizOptionOne,binding.quizOptionTwo,binding.quizOptionThree,binding.quizOptionFour)
            }
        }
    }

}