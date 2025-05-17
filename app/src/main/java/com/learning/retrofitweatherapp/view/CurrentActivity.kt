package com.learning.retrofitweatherapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.learning.retrofitweatherapp.R
import com.learning.retrofitweatherapp.adapter.CurrentAdapter
import com.learning.retrofitweatherapp.databinding.ActivityCurrentBinding
import com.learning.retrofitweatherapp.util.showError
import com.learning.retrofitweatherapp.viewmodel.WeatherViewModel

class CurrentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCurrentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        binding.apply {
            btSearch.setOnClickListener {
                viewModel.getCurrentData(etSearch.text.toString().trim())
            }
            btBack.setOnClickListener {
                onBackPressed()
            }
        }
        viewModel.currentLiveData.observe(this) { currentList ->
            currentList?.let {
                binding.rvCurrent.adapter = CurrentAdapter(currentList)
            }
        }
        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                showError(error)
                Log.e("CurrentError", error)
            }
        }
    }
}