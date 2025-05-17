package com.learning.retrofitweatherapp.model.dto.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponseItem(

	@SerializedName("country")
	val country: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("lon")
	val lon: Double? = null,

	@SerializedName("region")
	val region: String? = null,

	@SerializedName("lat")
	val lat: Double? = null
) : Parcelable
