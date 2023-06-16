package com.bangkit.onikku.vmFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.onikku.data.repository.MapRepository
import com.bangkit.onikku.di.MapInjection
import com.bangkit.onikku.ui.map.MapsViewModel

class MapsViewModelFactory(private val mapRepository: MapRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHEKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)){
            return MapsViewModel(mapRepository)as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: MapsViewModelFactory? = null

        fun getInstance(context: Context): MapsViewModelFactory =
            instance ?: synchronized(this){
                instance?: MapsViewModelFactory(MapInjection.provideMapRepository())
            }.also { instance = it }
    }
}