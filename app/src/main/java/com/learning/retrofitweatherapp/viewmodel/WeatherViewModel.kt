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

    private val _isLoading = MutableLiveData(false)
    val isLoading : MutableLiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage : LiveData<String?> = _errorMessage

    fun getSearchData(value : String){
        viewModelScope.launch {
            resetData()
            val searchResponse = weatherRepository.getSearchData(value)
            searchResponse.onSuccess {
                _searchLiveData.postValue(it)
            }
            searchResponse.onFailure {
                _errorMessage.postValue("Failed to search location : ${it.message}")
            }
            _isLoading.postValue(false)
        }
    }

    fun getCurrentData(value : String){
        viewModelScope.launch {
            resetData()
            val currentResponse = weatherRepository.getCurrentData(value)
            currentResponse.onSuccess {
                _currentLiveData.postValue(it)
            }
            currentResponse.onFailure {
                _errorMessage.postValue("Failed to load current data : ${it.message}")
            }
            _isLoading.postValue(false)
        }
    }

    fun getAstronomyData(value : String){
        viewModelScope.launch {
            val astronomyResponse = weatherRepository.getAstronomyData(value)
            resetData()
            astronomyResponse.onSuccess {
                _astronomyLiveData.postValue(it)
            }
            astronomyResponse.onFailure {
                _errorMessage.postValue("Failed to load astronomy data : ${it.message}")
            }
            _isLoading.postValue(false)
        }
    }

    private fun resetData(){
        _isLoading.postValue(true)
        _errorMessage.postValue(null)
    }
}