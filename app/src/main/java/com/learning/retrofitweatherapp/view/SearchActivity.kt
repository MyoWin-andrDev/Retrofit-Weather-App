package com.learning.retrofitweatherapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.learning.retrofitweatherapp.adapter.SearchAdapter
import com.learning.retrofitweatherapp.databinding.ActivitySearchBinding
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import com.learning.retrofitweatherapp.viewmodel.WeatherViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }


    private fun setupObservers() {
        viewModel.searchLiveData.observe(this) { either ->
            either.fold(
                ifLeft = { errMsg -> showError(errMsg) },
                ifRight = { searchList ->
                    if (searchList.isNotEmpty()) setupRecyclerView(searchList)
                }
            )
        }

    }

    private fun setupRecyclerView(searchList: List<SearchResponseItem>) {
        binding.rvSearch.adapter = SearchAdapter(searchList) { searchItem ->
            showDetailDialog(searchItem)
        }
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener { finish() }
        binding.btSearch.setOnClickListener {
            binding.etSearch.text.toString().trim().takeIf { it.isNotEmpty() }
                ?.let { query -> viewModel.getSearchData(query) }
                ?: showError("Please enter a search term.")
        }
    }


    private fun showDetailDialog(searchItem: SearchResponseItem) {
        val dialog = DetailDialogFragment.newInstance(searchItem)
        dialog.show(supportFragmentManager, "DetailDialog")
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
