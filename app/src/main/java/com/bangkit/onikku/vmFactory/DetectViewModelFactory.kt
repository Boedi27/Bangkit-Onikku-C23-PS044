package com.bangkit.onikku.vmFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.onikku.data.repository.DetectRepository
import com.bangkit.onikku.di.DetectInjection
import com.bangkit.onikku.di.MapInjection
import com.bangkit.onikku.ui.detection.DetectViewModel
import com.bangkit.onikku.ui.map.MapsViewModel

class DetectViewModelFactory (private val detectRepository: DetectRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHEKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetectViewModel::class.java)){
            return DetectViewModel(detectRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: DetectViewModelFactory? = null

        fun getInstance(context: Context): DetectViewModelFactory =
            instance ?: synchronized(this){
                instance?: DetectViewModelFactory(DetectInjection.provideDetectRepository())
            }.also { instance = it }
    }
}