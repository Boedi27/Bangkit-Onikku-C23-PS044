package com.bangkit.onikku.di

import com.bangkit.onikku.data.repository.MapRepository

object MapInjection {
    fun provideMapRepository():MapRepository{
        return MapRepository.getInstance()
    }
}