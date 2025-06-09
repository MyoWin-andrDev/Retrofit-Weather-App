package com.learning.retrofitweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.learning.retrofitweatherapp.model.dto.response.AstronomyResponse
import com.learning.retrofitweatherapp.model.dto.response.CurrentResponse
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import com.learning.retrofitweatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _searchLiveData = MutableLiveData<Either<String, List<SearchResponseItem>>>()
    val searchLiveData: LiveData<Either<String, List<SearchResponseItem>>> = _searchLiveData

    private val _currentLiveData = MutableLiveData<Either<String, List<CurrentResponse>>>()
    val currentLiveData: LiveData<Either<String, List<CurrentResponse>>> = _currentLiveData

    private val _astronomyLiveData = MutableLiveData<Either<String, List<AstronomyResponse>>>()
    val astronomyLiveData: LiveData<Either<String, List<AstronomyResponse>>> = _astronomyLiveData


    fun getSearchData(value: String) = viewModelScope.launch {
        weatherRepository.getSearchData(value)
            .onSuccess { list ->
                _searchLiveData.postValue(Either.Right(list))
            }.onFailure { throwable ->
                _searchLiveData.postValue(Either.Left("Failed to search location: ${throwable.message ?: "Unknown error"}"))
            }
    }

    fun getCurrentData(value: String) = viewModelScope.launch {
        weatherRepository.getCurrentData(value)
            .onSuccess { list ->
                _currentLiveData.postValue(Either.Right(list))
            }.onFailure { throwable ->
                _currentLiveData.postValue(Either.Left("Failed to load current data : ${throwable.message}"))
            }
    }

    fun getAstronomyData(value: String, date: String) = viewModelScope.launch {
        weatherRepository.getAstronomyData(value, date = date)
            .onSuccess { list ->
                _astronomyLiveData.postValue(Either.Right(list))
            }.onFailure { throwable ->
                _astronomyLiveData.postValue(Either.Left("Failed to load astronomy data : ${throwable.message}"))
            }
    }


}