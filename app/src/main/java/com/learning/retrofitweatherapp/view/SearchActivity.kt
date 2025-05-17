package com.learning.retrofitweatherapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.learning.retrofitweatherapp.R
import com.learning.retrofitweatherapp.adapter.SearchAdapter
import com.learning.retrofitweatherapp.databinding.ActivitySearchBinding
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import com.learning.retrofitweatherapp.util.showError
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
       }
        viewModel.searchLiveData.observe(this) { searchList ->
            searchList?.let {
                binding.rvSearch.adapter = SearchAdapter(searchList){ searchItem ->
                    startActivity(Intent(this@SearchActivity, DetailActivity::class.java).putExtra("SEARCH_ITEM", searchItem))
                }
            }
        }
        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                showError(error)
            }
        }
    }
}