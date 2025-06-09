package com.learning.retrofitweatherapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.learning.retrofitweatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() = with(binding) {
        tbMain.setNavigationOnClickListener {
            finish()
        }
        btSearch.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
        btCurrent.setOnClickListener {
            startActivity(Intent(this@MainActivity, CurrentActivity::class.java))
        }
        btAstronomy.setOnClickListener {
            startActivity(Intent(this@MainActivity, AstronomyActivity::class.java))
        }
    }
}