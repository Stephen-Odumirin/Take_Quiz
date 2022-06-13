package com.stdev.takequiz.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.stdev.takequiz.R
import com.stdev.takequiz.databinding.ActivityMainBinding
import com.stdev.takequiz.presentation.viewmodel.QuizViewModel
import com.stdev.takequiz.presentation.viewmodel.QuizViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: QuizViewModelFactory
    private lateinit var viewModel : QuizViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this,factory)[QuizViewModel::class.java]

//        viewModel.getQuiz(10,9,"easy","multiple")
//        viewModel.getQuiz2(10,9,"easy","multiple")
//
//        viewModel.quizList.observe(this){
//            Log.i("Main","1 ${it}")
//        }
//
//        binding.saveBtn.setOnClickListener {
//            viewModel.quiz.observe(this){
//                if (it!=null){
//                    Log.i("Main","2 ${it}")
//                    lifecycleScope.launch {
//                        viewModel.saveQuiz(it)
//                    }
//                }else{
//                    Log.i("Main","Null stuff")
//                }
//            }
//        }

    }

//    private suspend fun saveQuiz() {
//        delay(5000)
//        viewModel.quiz.observe(this){
//            if (it!=null){
//                lifecycleScope.launch {
//
//                    viewModel.saveQuiz(it)
//                }
//            }
//        }
//    }

}