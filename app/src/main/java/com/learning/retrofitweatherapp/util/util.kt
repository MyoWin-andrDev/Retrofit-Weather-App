package com.learning.retrofitweatherapp.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.learning.retrofitweatherapp.databinding.DialogSearchDetailBinding
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

fun Context.showToast(error : String){
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

//API Call
suspend fun <T, R> safeApiCall(
    apiCall : suspend () -> Response<T>,
    onSuccess : (T) -> R
    ) : Result<R>{
    return try {
        val response = apiCall()
        val body = response.body()
        if(response.isSuccessful && body != null){
            Result.success(onSuccess(body))
        }
        else{
            val errorMsg = response.errorBody()?.string()?: "Unknown Error"
            Result.failure(Exception("API Error : $errorMsg"))
        }
    }catch (e : Exception){
        Result.failure(e)
    }
}

//Detail Dialog
fun Context.showDetailDialog(item : SearchResponseItem){
    val dialogView = DialogSearchDetailBinding.inflate(LayoutInflater.from(this), null, false)
    with(dialogView){
        tvName.text = item.name
        tvCountry.text = item.country
        tvRegion.text = item.region
        tvLatitude.text = item.lat.toString()
        tvLongitude.text = item.lon.toString()
    }
    // Create and show dialog
    AlertDialog.Builder(this)
        .setView(dialogView.root)  // Use binding.root as the dialog view
        .setCancelable(true)
        .create()
        .apply {
            show()
            dialogView.btClose.setOnClickListener {
                dismiss()
            }
        }
}
