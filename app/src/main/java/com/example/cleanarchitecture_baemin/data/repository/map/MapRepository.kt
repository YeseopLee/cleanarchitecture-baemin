package com.example.cleanarchitecture_baemin.data.repository.map

import com.example.cleanarchitecture_baemin.data.response.address.AddressInfo
import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity

interface MapRepository {

    suspend fun getReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    ): AddressInfo?


}