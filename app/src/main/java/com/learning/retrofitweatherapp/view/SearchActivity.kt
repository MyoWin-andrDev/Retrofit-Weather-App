package com.learning.retrofitweatherapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.learning.retrofitweatherapp.adapter.SearchAdapter
import com.learning.retrofitweatherapp.databinding.ActivitySearchBinding
import com.learning.retrofitweatherapp.util.showDetailDialog
import com.learning.retrofitweatherapp.util.showToast
import com.learning.retrofitweatherapp.viewmodel.WeatherViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
       binding.apply {
           btSearch.setOnClickListener {
               viewModel.getSearchData(etSearch.text.toString().trim())
           }
           ibBack.setOnClickListener {
               onBackPressed()
           }
       }
        viewModel.searchLiveData.observe(this) { either ->
            either.fold(
                ifLeft = { error -> showToast(error)},
                ifRight = { searchList ->
                    binding.rvSearch.adapter = SearchAdapter(searchList){item ->
                    showDetailDialog(item)
                } }
            )
        }
    }
}