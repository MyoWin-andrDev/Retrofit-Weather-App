package com.learning.retrofitweatherapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.learning.retrofitweatherapp.adapter.AstronomyAdapter
import com.learning.retrofitweatherapp.databinding.ActivityAstronomyBinding
import com.learning.retrofitweatherapp.util.showError
import com.learning.retrofitweatherapp.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AstronomyActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAstronomyBinding
    private val stringDateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var selectedDateString: String = stringDateFormatter.format(Calendar.getInstance().time)
    private var inputDateFormat = apiDateFormat.format(Calendar.getInstance().time)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAstronomyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        binding.apply {
            btSearch.setOnClickListener {
                val location = etAstronomy.text.toString().trim()
                if (location.isNotEmpty()) {
                    viewModel.getAstronomyData(location, inputDateFormat)
                } else {
                    showError("Please Enter A City !!!.")
                }
            }
            btDatePicker.setOnClickListener {
                showDatePickerDialog()
            }
        }
        viewModel.astronomyLiveData.observe(this){ either ->
            either.fold(
                ifLeft = {error -> showError(error)},
                ifRight = {astronomyList ->  AstronomyAdapter(astronomyList)}
            )
        }

    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePicker = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText("Select date")
            .setSelection(calendar.timeInMillis)
            .build()

        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            inputDateFormat = apiDateFormat.format(selectedDate)
            selectedDateString = stringDateFormatter.format(selectedDate)
            binding.btDatePicker.text = selectedDateString
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
    }
}