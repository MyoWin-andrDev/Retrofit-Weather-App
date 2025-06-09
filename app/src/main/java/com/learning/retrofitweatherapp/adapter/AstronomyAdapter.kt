package com.learning.retrofitweatherapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.retrofitweatherapp.databinding.AstronomyListItemBinding
import com.learning.retrofitweatherapp.model.dto.response.AstronomyResponse

class AstronomyAdapter(val astronomyResponseList: List<AstronomyResponse>) :
    RecyclerView.Adapter<AstronomyAdapter.AstronomyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AstronomyViewHolder =
        AstronomyViewHolder(AstronomyListItemBinding.inflate(LayoutInflater.from(parent.context)))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: AstronomyViewHolder,
        position: Int
    ) {
        bindData(holder, position)
    }

    private fun bindData(holder: AstronomyViewHolder, position: Int) = with(holder.binding) {
        val locationList = astronomyResponseList[position].location
        val astronomyList = astronomyResponseList[position].astronomy?.astro

        tvCity.text = locationList?.name
        tvRegion.text = locationList?.region
        tvCountry.text = locationList?.country
        tvDistance.text = "${locationList?.distance} km"
        tvLocalTime.text = locationList?.localtime
        //Set Astronomy Data
        tvSunrise.text = astronomyList?.sunrise
        tvSunset.text = astronomyList?.sunset
        tvMoonrise.text = astronomyList?.moonrise
        tvMoonset.text = astronomyList?.moonset
    }

    override fun getItemCount(): Int = astronomyResponseList.size

    inner class AstronomyViewHolder(val binding: AstronomyListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}