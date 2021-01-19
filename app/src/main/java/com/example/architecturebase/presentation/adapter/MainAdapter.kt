package com.example.architecturebase.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.architecturebase.databinding.PostViewBinding
import com.example.architecturebase.domain.entities.Post

class MainAdapter : RecyclerView.Adapter<MainAdapter.Holder>() {

    var items: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    class Holder(private val binding: PostViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post) {
            binding.titleTV.text = item.title
            binding.bodyTV.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(
            binding = PostViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].id
}