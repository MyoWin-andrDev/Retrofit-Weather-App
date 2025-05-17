package com.learning.retrofitweatherapp.util

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale

fun Context.showError(error : String){
    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
}

fun formatDateTime(input: String): String {
    try {
        // Parse input format
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = inputFormat.parse(input)

        // Format to desired output
        val outputFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        return outputFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        return input // Return original if parsing fails
    }
}
