package com.bangkit.onikku.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecomendModel(
    val id: Int,
    val imgOlahan: String,
    val titleOlahan : String,
    val sortOlahan: String,
    val bahanOlahan: String,
    val alatOlahan: String,
    val langkahOlahan: String,
): Parcelable
