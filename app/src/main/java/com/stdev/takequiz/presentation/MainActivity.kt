package com.stdev.takequiz.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.stdev.takequiz.R
import com.stdev.takequiz.data.util.Constants
import com.stdev.takequiz.databinding.ActivityMainBinding
import com.stdev.takequiz.presentation.viewmodel.QuizViewModel
import com.stdev.takequiz.presentation.viewmodel.QuizViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}