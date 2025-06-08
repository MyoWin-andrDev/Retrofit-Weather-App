package com.learning.retrofitweatherapp.util

import android.content.Context
import android.widget.Toast
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
