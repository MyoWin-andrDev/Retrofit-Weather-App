package com.learning.retrofitweatherapp.repository

import android.util.Log
import com.learning.retrofitweatherapp.model.dto.response.AstronomyResponse
import com.learning.retrofitweatherapp.model.dto.response.CurrentResponse
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import com.learning.retrofitweatherapp.service.network.response.RetrofitInstance
import com.learning.retrofitweatherapp.service.network.response.WeatherAPIService
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class WeatherRepository {
    private val apiService = RetrofitInstance.getInstance().create(WeatherAPIService::class.java)

    suspend fun getSearchData(value : String) : Result<List<SearchResponseItem>>{
        return try {
            val response = apiService.getSearchData(cityInput = value)
            if(response.isSuccessful && response.body() != null){
                Result.success(response.body()!!)
            }
            else{
                Result.failure(Exception("Error :  ${response.errorBody()}"))
            }
        }catch(e : Exception){
            Result.failure(e)
        }
    }

    suspend fun getCurrentData(value : String) : Result<List<CurrentResponse>>{
        return try {
            val response = apiService.getCurrentData(cityInput = value)
            val body = response.body()
            if(response.isSuccessful && body != null){
                Result.success(listOf(body))

            }
            else{
                var errorMessage = response.errorBody()?.string()?: "Unknown Error"
                Result.failure(Exception("Error : $errorMessage"))
            }
        }
        catch (e : Exception){
            Result.failure(e)
        }
    }

    suspend fun getAstronomyData(value : String, date : String) : Result<List<AstronomyResponse>>{
        return try {
            val response = apiService.getAstronomyData(cityInput = value, date = date)
            if(response.isSuccessful && response.body() != null){
                //Calculate Distance From Yangon
                val astronomyResponse = response.body()!!
                val yangonLat = 16.7833
                val yangonLon = 96.1667
                val distance = calculateDistance(
                    yangonLat, yangonLon,
                    astronomyResponse.location?.lat, astronomyResponse.location?.lon
                )
                // Create modified response with distance and formatted time
                val modifiedResponse = astronomyResponse.copy(
                    location = astronomyResponse.location?.copy(
                        distance = distance,
                        localtime = formatAstronomyLocaltime(astronomyResponse.location.localtime.toString())
                    )
                )
                Result.success(listOf(modifiedResponse))
            }
            else{
                Result.failure(Exception("Error : ${response.errorBody()}"))
            }
        }
        catch (e : Exception){
            Result.failure(e)
        }
    }
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