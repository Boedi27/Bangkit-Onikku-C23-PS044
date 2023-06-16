package com.bangkit.onikku.vmFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.onikku.data.repository.RecomendRepository
import com.bangkit.onikku.di.RecomendInjection
import com.bangkit.onikku.ui.recomendation.RecomendationViewModel

class RecomendationViewModelFactory (private val recomendRepository: RecomendRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHEKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecomendationViewModel::class.java)){
            return RecomendationViewModel(recomendRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: RecomendationViewModelFactory? = null

        fun getInstance(context: Context): RecomendationViewModelFactory =
            instance ?: synchronized(this){
                instance?: RecomendationViewModelFactory(RecomendInjection.provideRecomendRepository())
            }.also { instance = it }
    }
}