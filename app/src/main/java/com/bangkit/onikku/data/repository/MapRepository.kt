package com.bangkit.onikku.data.repository

import com.bangkit.onikku.data.model.MapData
import com.bangkit.onikku.data.model.MapModel

class MapRepository {
    fun getMaps(): List<MapModel> {
        return MapData.maps
    }

    companion object {
        @Volatile
        private var instance: MapRepository? = null

        fun getInstance(): MapRepository =
            instance ?: synchronized(this) {
                MapRepository().apply {
                    instance = this
                }
            }
    }
}