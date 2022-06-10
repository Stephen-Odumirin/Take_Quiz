package com.stdev.takequiz.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.stdev.takequiz.R
import com.stdev.takequiz.presentation.viewmodel.QuizViewModel
import com.stdev.takequiz.presentation.viewmodel.QuizViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: QuizViewModelFactory
    private lateinit var viewModel : QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(this,factory)[QuizViewModel::class.java]

        viewModel.getQuiz(10,9,"easy","multiple")

        viewModel.quizList.observe(this){
            Log.i("Main","${it}")
        }

    }



}