package com.learning.retrofitweatherapp.model.dto.response

import com.google.gson.annotations.SerializedName

data class SearchResponseItem(

	@SerializedName("country")
	val country: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("lon")
	val lon: Any? = null,

	@SerializedName("region")
	val region: String? = null,

	@SerializedName("lat")
	val lat: Any? = null
)
