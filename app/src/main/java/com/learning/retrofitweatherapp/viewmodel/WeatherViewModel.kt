package com.learning.retrofitweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.learning.retrofitweatherapp.model.dto.response.AstronomyResponse
import com.learning.retrofitweatherapp.model.dto.response.CurrentResponse
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import com.learning.retrofitweatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    val weatherRepository = WeatherRepository()

    private val _searchLiveData = MutableLiveData<Either<String , List<SearchResponseItem>>>()
    val searchLiveData : LiveData<Either<String,List<SearchResponseItem>>> = _searchLiveData

    private val _currentLiveData = MutableLiveData<Either<String, List<CurrentResponse>>>()
    val currentLiveData : LiveData<Either<String, List<CurrentResponse>>> = _currentLiveData

    private val _astronomyLiveData = MutableLiveData<Either<String, List<AstronomyResponse>>>()
    val astronomyLiveData : LiveData<Either<String, List<AstronomyResponse>>> = _astronomyLiveData


    fun getSearchData(value : String){
        viewModelScope.launch {
            val searchResponse = weatherRepository.getSearchData(value)
            searchResponse.onSuccess { list ->
                _searchLiveData.postValue(Either.Right(list))
            }
            searchResponse.onFailure { throwable ->
                _searchLiveData.postValue(Either.Left("Failed to search location : ${throwable.message?: "Unknown Error"}"))
            }
        }
    }

    fun getCurrentData(value : String){
        viewModelScope.launch {
            val currentResponse = weatherRepository.getCurrentData(value)
            currentResponse.onSuccess { list ->
                _currentLiveData.postValue(Either.Right(list))
            }
            currentResponse.onFailure { throwable ->
                _currentLiveData.postValue(Either.Left("Failed to load current data : ${throwable.message?: "Unknown Error"}"))
            }
        }
    }

    fun getAstronomyData(value : String, date : String){
        viewModelScope.launch {
            val astronomyResponse = weatherRepository.getAstronomyData(value, date)
            astronomyResponse.onSuccess { list ->
                _astronomyLiveData.postValue(Either.Right(list))
            }
            astronomyResponse.onFailure { throwable ->
                _astronomyLiveData.postValue(Either.Left("Failed to load astronomy data : ${throwable.message?: "Unknown Error"}"))
            }
        }
    }
}