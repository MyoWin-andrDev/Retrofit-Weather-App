package com.learning.retrofitweatherapp.repository

import com.learning.retrofitweatherapp.model.dto.response.AstronomyResponse
import com.learning.retrofitweatherapp.model.dto.response.CurrentResponse
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import com.learning.retrofitweatherapp.service.network.response.RetrofitInstance
import com.learning.retrofitweatherapp.service.network.response.WeatherAPIService

class WeatherRepository {
    private val apiService = RetrofitInstance.getInstance().create(WeatherAPIService::class.java)

    suspend fun getSearchData(value : String) : Result<List<SearchResponseItem>>{
        val searchList = ArrayList<SearchResponseItem>()
        return try {
            val response = apiService.getSearchData(cityInput = value)
            if(response.isSuccessful && response.body() != null){
                searchList.addAll(response.body()!!)
                Result.success(searchList)
            }
            else{
                Result.failure(Exception("Error :  ${response.errorBody()}"))
            }
        }catch(e : Exception){
            Result.failure(e)
        }
    }

    suspend fun getCurrentData(value : String) : Result<List<CurrentResponse>>{
        val currentList = ArrayList<CurrentResponse>()
        return try {
            val response = apiService.getCurrentData(cityInput = value)
            if(response.isSuccessful && response.body() != null){
                currentList.addAll(response.body()!!)
                Result.success(currentList)
            }
            else{
                Result.failure(Exception("Error : ${response.errorBody()}"))
            }
        }
        catch (e : Exception){
            Result.failure(e)
        }
    }

    suspend fun getAstronomyData(value : String) : Result<List<AstronomyResponse>>{
        val astronomyList = ArrayList<AstronomyResponse>()
        return try {
            val response = apiService.getAstronomyData(cityInput = value)
            if(response.isSuccessful && response.body() != null){
                astronomyList.addAll(response.body()!!)
                Result.success(astronomyList)
            }
            else{
                Result.failure(Exception("Error : ${response.errorBody()}"))
            }
        }
        catch (e : Exception){
            Result.failure(e)
        }
    }
}