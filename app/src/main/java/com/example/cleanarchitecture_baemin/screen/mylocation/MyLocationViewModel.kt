package com.example.cleanarchitecture_baemin.screen.mylocation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture_baemin.R
import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity
import com.example.cleanarchitecture_baemin.data.entitiy.MapSearchInfoEntity
import com.example.cleanarchitecture_baemin.data.repository.map.MapRepository
import com.example.cleanarchitecture_baemin.data.repository.user.UserRepository
import com.example.cleanarchitecture_baemin.screen.base.BaseViewModel
import com.example.cleanarchitecture_baemin.screen.main.home.HomeState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyLocationViewModel(
    private val mapSearchInfoEntity: MapSearchInfoEntity,
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository
): BaseViewModel() {

    val myLocationStateLiveData = MutableLiveData<MyLocationState>(MyLocationState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        myLocationStateLiveData.value = MyLocationState.Loading
        myLocationStateLiveData.value = MyLocationState.Success(
            mapSearchInfoEntity
        )
    }

    fun changeLocationInfo(
        locationLatLngEntity: LocationLatLngEntity
    ) = viewModelScope.launch {
        val addressInfo = mapRepository.getReverseGeoInformation(locationLatLngEntity)
        addressInfo?.let { info ->
            myLocationStateLiveData.value = MyLocationState.Success(
                mapSearchInfoEntity = info.toSearchInfoEntity(locationLatLngEntity)
            )

        } ?: kotlin.run {
            myLocationStateLiveData.value = MyLocationState.Error(
                R.string.can_not_load_address_info
            )
        }
    }

    fun confirmSelectLocation() = viewModelScope.launch {
        when (val data = myLocationStateLiveData.value) {
            is MyLocationState.Success -> {
                userRepository.insertUserLocation(data.mapSearchInfoEntity.locationLatLng)
                Log.e("GoogleTest001", data.mapSearchInfoEntity.locationLatLng.toString())
                myLocationStateLiveData.value = MyLocationState.Confirm(
                    data.mapSearchInfoEntity
                )
            }
        }
    }

}