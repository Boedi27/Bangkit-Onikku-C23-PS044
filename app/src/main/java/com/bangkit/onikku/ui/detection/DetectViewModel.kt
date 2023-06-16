package com.bangkit.onikku.ui.detection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.onikku.data.network.ResultResponse
import com.bangkit.onikku.data.repository.DetectRepository
import java.io.File

class DetectViewModel(private val detectRepository: DetectRepository) : ViewModel() {

    private val _predictionResult = MutableLiveData<ResultResponse>()
    val predictionResult: LiveData<ResultResponse> = _predictionResult

    fun makePrediction(imageFile: File) {
        val resultLiveData = detectRepository.getPrediction(imageFile)
        resultLiveData.observeForever { result ->
            _predictionResult.value = result
        }
    }
}