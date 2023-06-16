package com.bangkit.onikku.data.network

import com.google.gson.annotations.SerializedName

data class ResultResponse(
	@field:SerializedName("prediction")
	val prediction: Int? = null
)
