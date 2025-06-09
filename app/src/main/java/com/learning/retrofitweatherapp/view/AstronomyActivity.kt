package com.learning.retrofitweatherapp.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.learning.retrofitweatherapp.adapter.AstronomyAdapter
import com.learning.retrofitweatherapp.databinding.ActivityAstronomyBinding
import com.learning.retrofitweatherapp.util.showToast
import com.learning.retrofitweatherapp.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AstronomyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAstronomyBinding
    private val viewModel: WeatherViewModel by viewModels()

    private val stringDateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val apiDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private var selectedDateString: String = stringDateFormatter.format(Calendar.getInstance().time)
    private var inputDateFormat: String = apiDateFormatter.format(Calendar.getInstance().time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAstronomyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeAstronomyData()
    }

    private fun setupListeners() = with(binding) {
        ibBack.setOnClickListener { finish() }
        btSearch.setOnClickListener {
            val location = etAstronomy.text.toString().trim()
            if (location.isNotEmpty()) {
                viewModel.getAstronomyData(location, inputDateFormat)
            } else {
                showToast("Please enter a city.")
            }
        }

        btDatePicker.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun observeAstronomyData() = with(binding) {
        viewModel.astronomyLiveData.observe(this@AstronomyActivity) { either ->
            either.fold(
                ifLeft = { errMsg -> showToast(errMsg) },
                ifRight = { astronomyList ->
                    if (astronomyList.isNotEmpty()) rvAstronomy.adapter =
                        AstronomyAdapter(astronomyList)
                }
            )
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(calendar.timeInMillis)
            .build()

        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            inputDateFormat = apiDateFormatter.format(selectedDate)
            selectedDateString = stringDateFormatter.format(selectedDate)
            binding.btDatePicker.text = selectedDateString
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
    }

}
