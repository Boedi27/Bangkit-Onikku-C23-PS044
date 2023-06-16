package com.bangkit.onikku.data.repository

import com.bangkit.onikku.data.model.RecomendData
import com.bangkit.onikku.data.model.RecomendModel

class RecomendRepository {
    fun getRecomend():List<RecomendModel>{
        return RecomendData.recomends
    }

    companion object{
        @Volatile
        private var instance: RecomendRepository? = null

        fun getinstance(): RecomendRepository =
            instance?: synchronized(this){
                RecomendRepository().apply {
                    instance = this
                }
            }
    }
}