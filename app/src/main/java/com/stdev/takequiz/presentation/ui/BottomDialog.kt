package com.stdev.takequiz.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stdev.takequiz.R
import com.stdev.takequiz.data.model.Category
import com.stdev.takequiz.data.util.Constants.any
import com.stdev.takequiz.data.util.Constants.easy
import com.stdev.takequiz.data.util.Constants.hard
import com.stdev.takequiz.data.util.Constants.medium
import com.stdev.takequiz.data.util.Constants.type_one
import com.stdev.takequiz.data.util.Constants.type_two
import com.stdev.takequiz.databinding.BottomDialogBinding

class BottomDialog : BottomSheetDialogFragment() {

    private lateinit var binding : BottomDialogBinding
    private var difficulty = "easy"
    private var type = "multiple"
    private var questions = 10
    private lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BottomDialogBinding.bind(view)
        category = BottomDialogArgs.fromBundle(requireArguments()).category
        binding.dialogCategoryText.text = category.name
        binding.dialogChipDifficultyGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                binding.dialogChipAny.id -> {difficulty = any}
                binding.dialogChipEasy.id -> {difficulty = easy}
                binding.dialogChipMedium.id -> {difficulty = medium}
                binding.dialogChipDifficult.id -> {difficulty = hard}
            }
        }

        binding.dialogChipTypeGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                binding.dialogChipMultiple.id -> {type = type_one}
                binding.dialogChipBoolean.id -> {type = type_two}
            }
        }

        binding.dialogCategoryButton.setOnClickListener {
            val _question = binding.dialogCategoryNoOfQuestions.editableText.toString()
            if (_question.isNotBlank()){
                questions = _question.toInt()
            }
            if (questions!=0 && questions<=50){
                val action = BottomDialogDirections.actionBottomDialogToQuizFragment(category,difficulty,type,questions.toString())
                findNavController().navigate(action)
            } else{
                Toast.makeText(context,"Enter a number between 1-50", Toast.LENGTH_SHORT).show()
            }

        }


    }

}