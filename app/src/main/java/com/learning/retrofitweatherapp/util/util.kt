package com.learning.retrofitweatherapp.util

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale

fun Context.showToast(error: String) {
    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
}

fun formatDateTime(input: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())

    return runCatching {
        inputFormat.parse(input)?.let { outputFormat.format(it) }
    }.getOrElse {
        it.printStackTrace()
        input
    }.toString()
}

