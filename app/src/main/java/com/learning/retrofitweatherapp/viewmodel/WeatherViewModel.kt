package com.learning.retrofitweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.retrofitweatherapp.model.dto.response.AstronomyResponse
import com.learning.retrofitweatherapp.model.dto.response.CurrentResponse
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import com.learning.retrofitweatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    val weatherRepository = WeatherRepository()

    private val _searchLiveData = MutableLiveData<List<SearchResponseItem>>()
    val searchLiveData : LiveData<List<SearchResponseItem>> = _searchLiveData

    private val _currentLiveData = MutableLiveData<List<CurrentResponse>>()
    val currentLiveData : LiveData<List<CurrentResponse>> = _currentLiveData

    private val _astronomyLiveData = MutableLiveData<List<AstronomyResponse>>()
    val astronomyLiveData : LiveData<List<AstronomyResponse>> = _astronomyLiveData

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage : LiveData<String?> = _errorMessage

    fun getSearchData(value : String){
        viewModelScope.launch {
            resetError()
            val searchResponse = weatherRepository.getSearchData(value)
            searchResponse.onSuccess {
                _searchLiveData.postValue(it)
            }
            searchResponse.onFailure {
                _errorMessage.postValue("Failed to search location : ${it.message}")
            }
        }
    }

    fun getCurrentData(value : String){
        viewModelScope.launch {
            resetError()
            val currentResponse = weatherRepository.getCurrentData(value)
            currentResponse.onSuccess {
                _currentLiveData.postValue(it)
            }
            currentResponse.onFailure {
                _errorMessage.postValue("Failed to load current data : ${it.message}")
            }
        }
    }

    fun getAstronomyData(value : String, date : String){
        viewModelScope.launch {
            val astronomyResponse = weatherRepository.getAstronomyData(value, date)
            resetError()
            astronomyResponse.onSuccess {
                _astronomyLiveData.postValue(it)
            }
            astronomyResponse.onFailure {
                _errorMessage.postValue("Failed to load astronomy data : ${it.message}")
            }
        }
    }

    private fun resetError(){
        _errorMessage.postValue(null)
    }
}