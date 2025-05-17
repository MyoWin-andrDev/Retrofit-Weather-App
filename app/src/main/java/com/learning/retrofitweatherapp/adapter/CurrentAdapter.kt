package com.learning.retrofitweatherapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.retrofitweatherapp.databinding.CurrentListItemBinding
import com.learning.retrofitweatherapp.model.dto.response.CurrentResponse
import com.learning.retrofitweatherapp.util.formatDateTime

class CurrentAdapter(val currentResponseList : List<CurrentResponse>) : RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrentViewHolder = CurrentViewHolder(CurrentListItemBinding.inflate(LayoutInflater.from(parent.context)))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: CurrentViewHolder,
        position: Int
    ) {
        val currentList = currentResponseList[position].current
        val locationList = currentResponseList[position].location
        holder.binding.apply {
            //Set Location Data
            tvLocationName.text = locationList?.name
            tvRegionCountry.text = "${locationList?.region}, ${locationList?.country}"
            tvLocalTime.text = formatDateTime(locationList?.localtime.toString())

            //Set Current Data
            tvTempC.text = "${currentList?.tempC.toString()}\u00B0C"
            tvTempF.text = "${currentList?.tempF.toString()}\u00B0C"
            tvCondition.text = currentList?.condition?.text
            tvWind.text = "${currentList?.windKph} kph (${currentList?.windDegree}\u00B0 , ${currentList?.windDir})"
            tvHumidity.text = currentList?.humidity.toString()
            tvUV.text = currentList?.uv.toString()
        }
    }

    override fun getItemCount(): Int = currentResponseList.size

    inner class CurrentViewHolder(val binding : CurrentListItemBinding) : RecyclerView.ViewHolder(binding.root)
}