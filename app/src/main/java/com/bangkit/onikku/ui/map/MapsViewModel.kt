package com.bangkit.onikku.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.onikku.data.model.MapModel
import com.bangkit.onikku.data.repository.MapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsViewModel(private val mapRepository: MapRepository) : ViewModel() {
    private val _mapsLiveData: MutableLiveData<List<MapModel>> = MutableLiveData()
    val mapsLiveData: LiveData<List<MapModel>> get() = _mapsLiveData

    fun getMaps() {
        viewModelScope.launch {
            val maps = withContext(Dispatchers.IO) {
                mapRepository.getMaps()
            }
            _mapsLiveData.value = maps
        }
    }
}