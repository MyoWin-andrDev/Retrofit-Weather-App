package com.learning.retrofitweatherapp.repository

import android.util.Log
import com.learning.retrofitweatherapp.model.dto.response.AstronomyResponse
import com.learning.retrofitweatherapp.model.dto.response.CurrentResponse
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import com.learning.retrofitweatherapp.service.network.response.RetrofitInstance
import com.learning.retrofitweatherapp.service.network.response.WeatherAPIService
import com.learning.retrofitweatherapp.util.safeApiCall
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class WeatherRepository {
    private val apiService = RetrofitInstance.getInstance().create(WeatherAPIService::class.java)

    suspend fun getSearchData(value : String) : Result<List<SearchResponseItem>> = safeApiCall(
        apiCall = { apiService.getSearchData(cityInput = value) },
        onSuccess = { it }
    )

    suspend fun getCurrentData(value : String) : Result<List<CurrentResponse>> = safeApiCall(
        apiCall = { apiService.getCurrentData(cityInput = value) },
        onSuccess = {listOf(it)}
    )

    suspend fun getAstronomyData(value : String, date : String) : Result<List<AstronomyResponse>> = safeApiCall(
        apiCall = { apiService.getAstronomyData(cityInput = value, date = date)},
        onSuccess = { body ->
            val updatedBody = body.copy(
                location = body.location?.copy(
                    distance = calculateDistance(
                        16.7833, 96.1667, body.location.lat, body.location.lon
                    ),
                    localtime = body.location.localtime?.let { formatAstronomyLocaltime(body.location.localtime) }
                )
            )
            listOf(updatedBody)
        }
    )
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double?, lon2: Double?): Double {
        val earthRadius = 6371 // Earth's radius in km
        val dLat = Math.toRadians(lat2!! - lat1)
        val dLon = Math.toRadians(lon2!! - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return "%.3f".format(earthRadius * c).toDouble()
    }

    private fun formatAstronomyLocaltime(localtime: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
            val date = inputFormat.parse(localtime)
            outputFormat.format(date)
        } catch (e: Exception) {
            localtime // Return original if parsing fails
        }
    }
}