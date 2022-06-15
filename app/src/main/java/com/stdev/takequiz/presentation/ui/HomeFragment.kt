package com.stdev.takequiz.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stdev.takequiz.R
import com.stdev.takequiz.data.model.Category
import com.stdev.takequiz.data.util.Constants.category_eight
import com.stdev.takequiz.data.util.Constants.category_eighteen
import com.stdev.takequiz.data.util.Constants.category_eleven
import com.stdev.takequiz.data.util.Constants.category_fifteen
import com.stdev.takequiz.data.util.Constants.category_five
import com.stdev.takequiz.data.util.Constants.category_four
import com.stdev.takequiz.data.util.Constants.category_fourteen
import com.stdev.takequiz.data.util.Constants.category_nine
import com.stdev.takequiz.data.util.Constants.category_nineteen
import com.stdev.takequiz.data.util.Constants.category_one
import com.stdev.takequiz.data.util.Constants.category_random
import com.stdev.takequiz.data.util.Constants.category_seven
import com.stdev.takequiz.data.util.Constants.category_seventeen
import com.stdev.takequiz.data.util.Constants.category_six
import com.stdev.takequiz.data.util.Constants.category_sixteen
import com.stdev.takequiz.data.util.Constants.category_ten
import com.stdev.takequiz.data.util.Constants.category_thirteen
import com.stdev.takequiz.data.util.Constants.category_three
import com.stdev.takequiz.data.util.Constants.category_twelve
import com.stdev.takequiz.data.util.Constants.category_twenty
import com.stdev.takequiz.data.util.Constants.category_twenty_four
import com.stdev.takequiz.data.util.Constants.category_twenty_one
import com.stdev.takequiz.data.util.Constants.category_twenty_three
import com.stdev.takequiz.data.util.Constants.category_twenty_two
import com.stdev.takequiz.data.util.Constants.category_two
import com.stdev.takequiz.data.util.Constants.type_one
import com.stdev.takequiz.databinding.FragmentHomeBinding
import com.stdev.takequiz.presentation.adapter.HomeAdapter

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        setUpRecyclerView()

        binding.homeRandomQuizButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToBottomDialog(category_random)//Category(0,""),"", type_one,"10"
            findNavController().navigate(action)
        }

        binding.homeSavedQuizButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_savedQuizFragment)
        }

    }

    private fun setUpRecyclerView(){
        homeAdapter = HomeAdapter()
        val list = listOf(category_one, category_two, category_three, category_four, category_five,
            category_six, category_seven, category_eight, category_nine, category_ten,
            category_eleven, category_twelve, category_thirteen, category_fourteen, category_fifteen,
            category_sixteen, category_seventeen, category_eighteen, category_nineteen,
            category_twenty, category_twenty_one, category_twenty_two, category_twenty_three,
            category_twenty_four)
        homeAdapter.differ.submitList(list)
        binding.homeRecyclerView.adapter = homeAdapter
        homeAdapter.setOnItemClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToBottomDialog(it)
            findNavController().navigate(action)
        }
    }

}