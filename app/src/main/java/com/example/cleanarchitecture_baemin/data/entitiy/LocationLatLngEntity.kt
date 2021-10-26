package com.example.cleanarchitecture_baemin.data.entitiy

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class LocationLatLngEntity(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1
): Parcelable