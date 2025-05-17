package com.learning.retrofitweatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.retrofitweatherapp.databinding.SearchListItemBinding
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem

class SearchAdapter(val searchResponseList : List<SearchResponseItem>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder = SearchViewHolder(SearchListItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(
        holder: SearchViewHolder,
        position: Int
    ) {
        holder.binding.apply {

        }
    }

    override fun getItemCount(): Int = searchResponseList.size

    inner class SearchViewHolder(val binding : SearchListItemBinding) : RecyclerView.ViewHolder(binding.root)
}