package com.bangkit.onikku.di

import com.bangkit.onikku.data.repository.RecomendRepository

object RecomendInjection {
    fun provideRecomendRepository(): RecomendRepository{
        return RecomendRepository.getinstance()
    }
}