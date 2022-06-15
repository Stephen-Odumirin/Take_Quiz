package com.stdev.takequiz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stdev.takequiz.data.model.Category
import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.databinding.FragmentSavedQuizBinding
import com.stdev.takequiz.databinding.SingleListItemBinding

class SavedAdapter : RecyclerView.Adapter<SavedAdapter.SavedViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Quiz>() {
        override fun areItemsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,callback)

    private var onItemClickListener : ((Quiz)-> Unit) = {}

    fun setOnButtonClickListener(listener : (Quiz)-> Unit){
        onItemClickListener = listener
    }

    inner class SavedViewHolder(val binding: SingleListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(quiz: Quiz){
            binding.textView.text = quiz.name
            binding.button2.setOnClickListener {
                onItemClickListener(quiz)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val binding = SingleListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SavedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.bindData(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}