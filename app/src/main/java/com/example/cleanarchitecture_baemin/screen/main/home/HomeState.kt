package com.example.cleanarchitecture_baemin.screen.main.home

import androidx.annotation.StringRes
import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity
import com.example.cleanarchitecture_baemin.data.entitiy.MapSearchInfoEntity

sealed class HomeState {

    object Uninitialized: HomeState()

    object Loading: HomeState()

    data class Success(
        val mapSearchInfo: MapSearchInfoEntity,
        val isLocationSame: Boolean
    ) : HomeState()

    data class Error(
        @StringRes val messageId: Int
    ): HomeState()

}
