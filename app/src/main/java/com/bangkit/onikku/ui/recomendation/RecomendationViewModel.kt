package com.bangkit.onikku.ui.recomendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.onikku.data.model.RecomendModel
import com.bangkit.onikku.data.repository.RecomendRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecomendationViewModel (private val recomendRepository: RecomendRepository) : ViewModel() {
    private val _recomendLiveData: MutableLiveData<List<RecomendModel>> = MutableLiveData()
    val recomendLiveData: LiveData<List<RecomendModel>> get() = _recomendLiveData

    fun getRecomend() {
        viewModelScope.launch {
            val recomend = withContext(Dispatchers.IO) {
                recomendRepository.getRecomend()
            }
            _recomendLiveData.value = recomend
        }
    }
}