package com.bangkit.onikku.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.onikku.data.network.ApiConfig
import com.bangkit.onikku.data.network.ApiService
import com.bangkit.onikku.data.network.ResultResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DetectRepository {
    private val apiService: ApiService = ApiConfig.getApiServiceImageUploader()

    fun getPrediction(imageFile: File): LiveData<ResultResponse> {
        val resultLiveData = MutableLiveData<ResultResponse>()

        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)

        val call = apiService.getPrediction(filePart)
        call.enqueue(object : Callback<ResultResponse> {
            override fun onResponse(
                call: Call<ResultResponse>,
                response: Response<ResultResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    resultLiveData.postValue(result!!)
                } else {
                    // Handle error response
                    Log.e("Failure", "Response Repository not success")
                }
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                // Handle failure
                Log.e("Failure", "${t.message}")
            }
        })

        return resultLiveData
    }

    companion object {
        @Volatile
        private var instance: DetectRepository? = null

        fun getInstance(): DetectRepository =
            instance ?: synchronized(this) {
                DetectRepository().apply {
                    instance = this
                }
            }
    }
}