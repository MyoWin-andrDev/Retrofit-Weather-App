package com.learning.retrofitweatherapp.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.learning.retrofitweatherapp.adapter.CurrentAdapter
import com.learning.retrofitweatherapp.databinding.ActivityCurrentBinding
import com.learning.retrofitweatherapp.util.showToast
import com.learning.retrofitweatherapp.viewmodel.WeatherViewModel

class CurrentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupListeners()
        observeCurrentData()


    }

    private fun setupListeners() = with(binding) {
        btSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.getCurrentData(query)
            }
        }

        ibBack.setOnClickListener {
            finish()
        }

    }


    private fun observeCurrentData() = with(binding) {
        viewModel.currentLiveData.observe(this@CurrentActivity) { either ->
            either.fold(
                ifLeft = { errMsg -> showToast(errMsg) },
                ifRight = { currentList ->
                    if (currentList.isNotEmpty()) rvCurrent.adapter = CurrentAdapter(currentList)
                }
            )
        }
    }
}

