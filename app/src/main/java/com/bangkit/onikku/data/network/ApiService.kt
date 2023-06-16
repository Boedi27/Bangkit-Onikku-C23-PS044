package com.bangkit.onikku.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ApiService {


    @Multipart
    @POST("/status")
    fun getPrediction(
        @Part image: MultipartBody.Part
    ): Call<ResultResponse>
}