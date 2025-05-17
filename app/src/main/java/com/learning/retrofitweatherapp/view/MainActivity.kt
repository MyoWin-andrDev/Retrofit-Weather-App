package com.learning.retrofitweatherapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.learning.retrofitweatherapp.R
import com.learning.retrofitweatherapp.databinding.ActivityMainBinding
import com.learning.retrofitweatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
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
}