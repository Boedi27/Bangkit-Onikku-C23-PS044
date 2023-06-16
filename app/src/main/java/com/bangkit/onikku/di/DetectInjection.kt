package com.bangkit.onikku.di

import com.bangkit.onikku.data.repository.DetectRepository

object DetectInjection {
    fun provideDetectRepository(): DetectRepository {
        return DetectRepository.getInstance()
    }
}