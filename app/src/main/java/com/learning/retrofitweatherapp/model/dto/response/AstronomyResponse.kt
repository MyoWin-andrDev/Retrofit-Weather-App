package com.learning.retrofitweatherapp.model.dto.response

import com.google.gson.annotations.SerializedName

data class AstronomyResponse(

	@SerializedName("location")
	val location: AstroLocationResponseItem? = null,

	@SerializedName("astronomy")
	val astronomy: Astronomy? = null
)

data class AstroLocationResponseItem(

	@SerializedName("localtime")
	val localtime: String? = null,

	@SerializedName("country")
	val country: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("region")
	val region: String? = null,

	@SerializedName("lat")
	val lat : Double? = null,

	@SerializedName("lon")
	val lon : Double? = null,

	val distance : Double? = null
)

data class AstroResponseItem(

	@SerializedName("moonset")
	val moonset: String? = null,

	@SerializedName("sunrise")
	val sunrise: String? = null,

	@SerializedName("sunset")
	val sunset: String? = null,

	@SerializedName("moonrise")
	val moonrise: String? = null
)

data class Astronomy(

	@SerializedName("astro")
	val astro: AstroResponseItem? = null
)
