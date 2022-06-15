package com.stdev.takequiz.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stdev.takequiz.R
import com.stdev.takequiz.data.model.Question
import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import com.stdev.takequiz.data.util.Constants
import com.stdev.takequiz.domain.usecase.GetQuizUseCase
import com.stdev.takequiz.domain.usecase.GetQuizWithIdUseCase
import com.stdev.takequiz.domain.usecase.SaveQuizUseCase
import kotlinx.coroutines.launch

class QuizViewModel(
    private val getQuizUseCase: GetQuizUseCase,
    private val saveQuizUseCase: SaveQuizUseCase,
    private val getQuizWithIdUseCase: GetQuizWithIdUseCase
) : ViewModel() {

    private val _quiz : MutableLiveData<Quiz> = MutableLiveData()
    val quiz : LiveData<Quiz> get() = _quiz

    private val _currentQuestion : MutableLiveData<Int> = MutableLiveData()
    val currentQuestion : LiveData<Int> get() = _currentQuestion

    private val _currentQuiz : MutableLiveData<Question> = MutableLiveData()
    val currentQuiz : LiveData<Question> get() = _currentQuiz

    private val _correctAnswers : MutableLiveData<Int> = MutableLiveData()
    val correctAnswers : LiveData<Int> get() = _correctAnswers


    private val _movetonext : MutableLiveData<Boolean> = MutableLiveData()
    val movetonext : LiveData<Boolean> get() = _movetonext
    private val _cananswer : MutableLiveData<Boolean> = MutableLiveData()

    private val _questionList : MutableLiveData<List<QuizResult>> = MutableLiveData()

    private val _questionList2 : MutableLiveData<MutableList<Question>> = MutableLiveData()
    val questionList2 : LiveData<MutableList<Question>> get() = _questionList2

    private val _status : MutableLiveData<Boolean> = MutableLiveData()
    val status : LiveData<Boolean> get() = _status

    private val _buttonText : MutableLiveData<String> = MutableLiveData()
    val buttonText : LiveData<String>  get() = _buttonText

    private val _isFinished : MutableLiveData<Boolean> = MutableLiveData()

    private val _quizType : MutableLiveData<Boolean> = MutableLiveData()
    val quizType : LiveData<Boolean> get() = _quizType

    init {
        _isFinished.value = false
        _correctAnswers.value = 0
        _currentQuestion.value = 0
        _correctAnswers.value = 0
        _movetonext.value = false
        _cananswer.value = false
        _questionList2.value = mutableListOf()
    }

    fun getQuizFromDb(id : String) = viewModelScope.launch {
        _quiz.value = getQuizWithIdUseCase.execute(id.toLong())
        if(_quiz.value != null){
            if (_quiz.value?.responseCode == 0){
                _status.value = true
                _questionList.value = _quiz.value?.results
                _quizType.value = _quiz.value!!.results[0].type == Constants.type_one
                convertFromQuizResultToQuestions()
                nextQuestion()
            }else{
                _status.value = false
            }
        }else{
            _status.value = false
        }
    }

    fun getQuiz(amount : Int, category : Int, difficulty : String, type : String) = viewModelScope.launch {
        val result = getQuizUseCase.execute(amount, category, difficulty, type)
        _quiz.value = result
        if(_quiz.value != null){
            if (_quiz.value?.responseCode == 0){
                _status.value = true
                _questionList.value = _quiz.value?.results
                _quizType.value = type == Constants.type_one
                convertFromQuizResultToQuestions()
                nextQuestion()
            }else{
                _status.value = false
            }
        }else{
            _status.value = false
        }
    }

    fun startAgain(){
        _isFinished.value = false
        _correctAnswers.value = 0
        _currentQuestion.value = 0
        _correctAnswers.value = 0
        _movetonext.value = false
        _cananswer.value = false
        _questionList2.value = mutableListOf()
    }

    fun saveQuiz(name : String) = viewModelScope.launch {
        val quiz = Quiz(name = name, responseCode = 0, results = quiz.value!!.results)
        saveQuizUseCase.execute(quiz)
    }

    private fun convertFromQuizResultToQuestions(){

        for (i in _questionList.value?.indices!!){
            val question = Question("$i","null","null","null","null","null",0)
            _questionList2.value?.add(question)
        }

        for (i in _questionList.value?.indices!!){
            _questionList2.value?.get(i)?.id  = i.toString()
            _questionList2.value?.get(i)?.question = _questionList.value?.get(i)?.question.toString()
            val answer: Int = if (_quizType.value == true){
                listOf(1,2,3,4).random()
            }else{
                listOf(1,2).random()
            }
            //val answer = listOf(1,2,3,4).random()
            if (_quizType.value == true){
                when(answer){
                    1 -> {
                        _questionList2.value?.get(i)?.optionOne = _questionList.value?.get(i)?.correctAnswer.toString()
                        _questionList2.value?.get(i)?.optionTwo = _questionList.value?.get(i)?.incorrectAnswers?.get(0).toString()
                        _questionList2.value?.get(i)?.optionThree = _questionList.value?.get(i)?.incorrectAnswers?.get(1).toString()
                        _questionList2.value?.get(i)?.optionFour = _questionList.value?.get(i)?.incorrectAnswers?.get(2).toString()
                        _questionList2.value?.get(i)?.correctAnswer = 1
                    }
                    2 -> {
                        _questionList2.value?.get(i)?.optionOne = _questionList.value?.get(i)?.incorrectAnswers?.get(0).toString()
                        _questionList2.value?.get(i)?.optionTwo = _questionList.value?.get(i)?.correctAnswer.toString()
                        _questionList2.value?.get(i)?.optionThree = _questionList.value?.get(i)?.incorrectAnswers?.get(1).toString()
                        _questionList2.value?.get(i)?.optionFour = _questionList.value?.get(i)?.incorrectAnswers?.get(2).toString()
                        _questionList2.value?.get(i)?.correctAnswer = 2
                    }
                    3 -> {
                        _questionList2.value?.get(i)?.optionOne = _questionList.value?.get(i)?.incorrectAnswers?.get(0).toString()
                        _questionList2.value?.get(i)?.optionTwo = _questionList.value?.get(i)?.incorrectAnswers?.get(1).toString()
                        _questionList2.value?.get(i)?.optionThree = _questionList.value?.get(i)?.correctAnswer.toString()
                        _questionList2.value?.get(i)?.optionFour = _questionList.value?.get(i)?.incorrectAnswers?.get(2).toString()
                        _questionList2.value?.get(i)?.correctAnswer = 3
                    }
                    4 -> {
                        _questionList2.value?.get(i)?.optionOne = _questionList.value?.get(i)?.incorrectAnswers?.get(0).toString()
                        _questionList2.value?.get(i)?.optionTwo = _questionList.value?.get(i)?.incorrectAnswers?.get(1).toString()
                        _questionList2.value?.get(i)?.optionThree = _questionList.value?.get(i)?.incorrectAnswers?.get(2).toString()
                        _questionList2.value?.get(i)?.optionFour = _questionList.value?.get(i)?.correctAnswer.toString()
                        _questionList2.value?.get(i)?.correctAnswer = 4
                    }
                }
            }else{
                when(answer) {
                    1 -> {
                        _questionList2.value?.get(i)?.optionOne = _questionList.value?.get(i)?.correctAnswer.toString()
                        _questionList2.value?.get(i)?.optionTwo = _questionList.value?.get(i)?.incorrectAnswers?.get(0).toString()
                        _questionList2.value?.get(i)?.optionThree = ""//_questionList.value?.get(i)?.incorrectAnswers?.get(1).toString()
                        _questionList2.value?.get(i)?.optionFour = "" //_questionList.value?.get(i)?.incorrectAnswers?.get(2).toString()
                        _questionList2.value?.get(i)?.correctAnswer = 1
                    }
                    2 -> {
                        _questionList2.value?.get(i)?.optionOne = _questionList.value?.get(i)?.incorrectAnswers?.get(0).toString()
                        _questionList2.value?.get(i)?.optionTwo = _questionList.value?.get(i)?.correctAnswer.toString()
                        _questionList2.value?.get(i)?.optionThree = ""//_questionList.value?.get(i)?.incorrectAnswers?.get(1).toString()
                        _questionList2.value?.get(i)?.optionFour = ""//_questionList.value?.get(i)?.incorrectAnswers?.get(2).toString()
                        _questionList2.value?.get(i)?.correctAnswer = 2
                    }
                }
            }


        }


    }

    fun endQuiz(){
        _isFinished.value = true
    }

    fun nextQuestion(){
        Log.i("ViewModel","Question Number ${_currentQuestion.value}")
        Log.i("ViewModel","Correct answers ${_correctAnswers.value}")
        if (!_isFinished.value!!){
            _cananswer.value = true
            _movetonext.value = false
            _currentQuiz.value = _questionList2.value?.get(_currentQuestion.value!!)
            _currentQuestion.value = (_currentQuestion.value)?.plus(1) ?: 1

            if (_currentQuestion.value == _questionList2.value!!.size){
                _buttonText.value = "Submit"
            }else{
                _buttonText.value = "Next"
            }
        }
    }

    fun verityAnswer(answerId : Int,textView: TextView,context : Context, optionOne : TextView, optionTwo : TextView, optionThree : TextView, optionFour : TextView){
        _movetonext.value = true
        if(_cananswer.value == true){
            if(answerId == _questionList2.value!![(_currentQuestion.value)!!.minus(1)].correctAnswer){
                _correctAnswers.value = _correctAnswers.value!!.plus(1)
                textView.background = ContextCompat.getDrawable(context, R.drawable.correct_answer)
            }else{
                textView.background = ContextCompat.getDrawable(context, R.drawable.wrong_answer)
                when(_questionList2.value!![_currentQuestion.value!!-1].correctAnswer){
                    1 -> {optionOne.background = ContextCompat.getDrawable(context, R.drawable.correct_answer)}
                    2 -> {optionTwo.background = ContextCompat.getDrawable(context, R.drawable.correct_answer)}
                    3 -> {optionThree.background = ContextCompat.getDrawable(context, R.drawable.correct_answer)}
                    4 -> {optionFour.background = ContextCompat.getDrawable(context, R.drawable.correct_answer)}
                }
            }
        }
        _cananswer.value = false
    }


}