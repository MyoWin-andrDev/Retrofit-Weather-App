package com.learning.retrofitweatherapp.model.dto.response

import com.google.gson.annotations.SerializedName

data class AstronomyResponse(

	@field:SerializedName("location")
	val location: AstroLocationResponseItem? = null,

	@field:SerializedName("astronomy")
	val astronomy: Astronomy? = null
)

data class AstroLocationResponseItem(

	@field:SerializedName("localtime")
	val localtime: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("region")
	val region: String? = null
)

data class AstroResponseItem(

	@field:SerializedName("moonset")
	val moonset: String? = null,

	@field:SerializedName("sunrise")
	val sunrise: String? = null,

	@field:SerializedName("sunset")
	val sunset: String? = null,

	@field:SerializedName("moonrise")
	val moonrise: String? = null
)

data class Astronomy(

	@field:SerializedName("astro")
	val astro: AstroResponseItem? = null
)
