package com.learning.retrofitweatherapp.model.dto.response

import com.google.gson.annotations.SerializedName

data class CurrentResponse(

	@SerializedName("current")
	val current: CurrentResponseItem? = null,

	@SerializedName("location")
	val location: LocationResponseItem? = null
)

data class CurrentResponseItem(

	@SerializedName("wind_kph")
	val windKph: Any? = null,

	@SerializedName("uv")
	val uv: Any? = null,

	@SerializedName("condition")
	val condition: ConditionResponseItem? = null,

	@SerializedName("wind_degree")
	val windDegree: Int? = null,

	@SerializedName("humidity")
	val humidity: Int? = null,

	@SerializedName("wind_dir")
	val windDir: String? = null,

	@SerializedName("temp_c")
	val tempC: Any? = null,

	@SerializedName("temp_f")
	val tempF: Any? = null
)

data class ConditionResponseItem(

	@SerializedName("text")
	val text: String? = null
)

data class LocationResponseItem(

	@SerializedName("localtime")
	val localtime: String? = null,

	@SerializedName("country")
	val country: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("region")
	val region: String? = null
)
