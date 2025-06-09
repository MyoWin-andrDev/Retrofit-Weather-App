package com.learning.retrofitweatherapp.repository

import com.learning.retrofitweatherapp.model.dto.response.AstronomyResponse
import com.learning.retrofitweatherapp.model.dto.response.CurrentResponse
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import com.learning.retrofitweatherapp.service.network.response.RetrofitInstance
import com.learning.retrofitweatherapp.service.network.response.WeatherAPIService
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class WeatherRepository {
    private val apiService = RetrofitInstance.getInstance().create(WeatherAPIService::class.java)

    suspend fun getSearchData(value: String): Result<List<SearchResponseItem>> = safeApiCall(
        apiCall = { apiService.getSearchData(cityInput = value) },
        onSuccess = { it }
    )

    suspend fun getCurrentData(value: String): Result<List<CurrentResponse>> = safeApiCall(
        apiCall = { apiService.getCurrentData(cityInput = value) },
        onSuccess = { listOf(it) }
    )


    suspend fun getAstronomyData(value: String, date: String): Result<List<AstronomyResponse>> =
        safeApiCall(
            apiCall = { apiService.getAstronomyData(cityInput = value, date = date) },
            onSuccess = { body ->
                val updated = body.copy(
                    location = body.location?.copy(
                        distance = calculateDistance(
                            16.7833, 96.1667,
                            body.location.lat, body.location.lon
                        ),
                        localtime = formatAstronomyLocaltime(body.location.localtime)
                    )
                )
                listOf(updated)
            }
        )


    private fun calculateDistance(
        lat1: Double,
        lon1: Double,
        lat2: Double?,
        lon2: Double?
    ): Double {
        if (lat2 == null || lon2 == null) return 0.0

        val earthRadiusKm = 6371.0

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return String.format("%.3f", earthRadiusKm * c).toDouble()
    }


    private fun formatAstronomyLocaltime(localtime: String?): String {
        if (localtime.isNullOrBlank()) return ""

        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
            inputFormat.parse(localtime)?.let { outputFormat.format(it) } ?: localtime
        } catch (e: Exception) {
            localtime
        }
    }

    private suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<T>,
        onSuccess: (T) -> R
    ): Result<R> {
        return try {
            val response = apiCall()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(onSuccess(body))
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("API Error: $errorMsg"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}