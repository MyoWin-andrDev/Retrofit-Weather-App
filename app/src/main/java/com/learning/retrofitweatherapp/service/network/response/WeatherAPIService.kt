package com.learning.retrofitweatherapp.service.network.response

import com.learning.retrofitweatherapp.model.dto.response.AstronomyResponse
import com.learning.retrofitweatherapp.model.dto.response.CurrentResponse
import com.learning.retrofitweatherapp.model.dto.response.CurrentResponseItem
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem
import com.learning.retrofitweatherapp.util.APIKEY
import com.learning.retrofitweatherapp.util.ASTRONOMY_END_POINT
import com.learning.retrofitweatherapp.util.CURRENT_END_POINT
import com.learning.retrofitweatherapp.util.SEARCH_END_POINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPIService {
    //Search Response
    @GET(SEARCH_END_POINT)
    suspend fun getSearchData(
        @Query("key")
        key : String = APIKEY,
        @Query("q")
        cityInput : String
    ) : Response<List<SearchResponseItem>>

    //Current Response
    @GET(CURRENT_END_POINT)
    suspend fun getCurrentData(
        @Query("key")
        key: String = APIKEY,
        @Query("q")
        cityInput : String
    ) : Response<CurrentResponse>

    //Astronomy Response
    @GET(ASTRONOMY_END_POINT)
    suspend fun getAstronomyData(
        @Query("key")
        key : String = APIKEY,
        @Query("q")
        cityInput : String,
        @Query("dt")
        date : String,
    ) : Response<AstronomyResponse>
}