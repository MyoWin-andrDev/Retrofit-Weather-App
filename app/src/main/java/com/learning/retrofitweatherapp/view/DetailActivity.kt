package com.learning.retrofitweatherapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.learning.retrofitweatherapp.R
import com.learning.retrofitweatherapp.databinding.ActivityDetailBinding
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val searchItem  = intent?.getParcelableExtra<SearchResponseItem>("SEARCH_ITEM")
        if(searchItem != null){
            binding.apply {
                tvName.text = searchItem.name
                tvCountry.text = searchItem.country
                tvRegion.text = searchItem.region
                tvLatitude.text = searchItem.lat.toString()
                tvLongitude.text = searchItem.lon.toString()
            }
        }
        binding.btClose.setOnClickListener {
            onBackPressed()
        }
    }
}