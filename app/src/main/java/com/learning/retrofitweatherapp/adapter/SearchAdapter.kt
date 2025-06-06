package com.learning.retrofitweatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.retrofitweatherapp.databinding.SearchListItemBinding
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem

class SearchAdapter(val searchResponseList : List<SearchResponseItem>, val onItemClick : (SearchResponseItem) -> Unit) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder = SearchViewHolder(SearchListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(
        holder: SearchViewHolder,
        position: Int
    ) {
        holder.binding.apply {
            tvName.text = searchResponseList[position].name
            tvCountry.text = searchResponseList[position].country
            btDetail.setOnClickListener {
                onItemClick(searchResponseList[position])
            }
        }
    }

    override fun getItemCount(): Int = searchResponseList.size

    inner class SearchViewHolder(val binding : SearchListItemBinding) : RecyclerView.ViewHolder(binding.root)
}