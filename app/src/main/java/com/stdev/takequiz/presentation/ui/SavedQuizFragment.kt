package com.stdev.takequiz.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.stdev.takequiz.R
import com.stdev.takequiz.databinding.FragmentSavedQuizBinding
import com.stdev.takequiz.presentation.adapter.SavedAdapter
import com.stdev.takequiz.presentation.viewmodel.SavedQuizViewModel
import com.stdev.takequiz.presentation.viewmodel.SavedQuizViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedQuizFragment : Fragment() {

    private lateinit var binding : FragmentSavedQuizBinding

    @Inject
    lateinit var factory: SavedQuizViewModelFactory
    private lateinit var viewModel : SavedQuizViewModel
    @Inject
    lateinit var adapter : SavedAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved_quiz,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSavedQuizBinding.bind(view)
        viewModel = ViewModelProvider(this,factory)[SavedQuizViewModel::class.java]

        binding.savedBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.getSavedQuiz().observe(viewLifecycleOwner){
            adapter.differ.submitList(it)
        }

        setUpRecyclerView()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val quiz = adapter.differ.currentList[position]
                viewModel.deleteQuiz(quiz)
                Snackbar.make(view,"Deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveQuiz(quiz)
                    }
                    show()
                }
            }

        }


        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.savedRecyclerView)

    }



    private fun setUpRecyclerView() {
        binding.savedRecyclerView.adapter = adapter
        adapter.setOnButtonClickListener {
            val action = SavedQuizFragmentDirections.actionSavedQuizFragmentToQuizFragment(null,null,null,null,true, it.id.toString())
            findNavController().navigate(action)
        }

    }

}