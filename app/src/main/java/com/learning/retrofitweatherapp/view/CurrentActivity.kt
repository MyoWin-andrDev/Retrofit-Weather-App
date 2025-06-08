package com.learning.retrofitweatherapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.learning.retrofitweatherapp.adapter.CurrentAdapter
import com.learning.retrofitweatherapp.databinding.ActivityCurrentBinding
import com.learning.retrofitweatherapp.util.showToast
import com.learning.retrofitweatherapp.viewmodel.WeatherViewModel

class CurrentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCurrentBinding
     private val viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListener()
        observeCurrentData()
    }
    private fun setupListener() = with(binding){
        btSearch.setOnClickListener {
            etSearch.text.toString().trim().takeIf { it.isNotEmpty() }?.let { query->
                viewModel.getCurrentData(query) }
                ?: "Please enter a location"

        }
        ibBack.setOnClickListener {
            onBackPressed()
        }
    }
    private fun observeCurrentData() = with(binding){
        viewModel.currentLiveData.observe(this@CurrentActivity){  either ->
            either.fold(
                ifLeft = { error -> showToast(error)},
                ifRight = { currentList ->
                    if(!currentList.isEmpty()){
                        binding.rvCurrent.adapter = CurrentAdapter(currentList)
                    }
                }
            )
        }
    }
}