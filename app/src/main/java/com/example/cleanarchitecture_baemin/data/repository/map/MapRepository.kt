package com.example.cleanarchitecture_baemin.data.repository.map

import aop.fastcampus.part6.chapter01.data.response.address.AddressInfo
import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity

interface MapRepository {

    suspend fun getReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    ): AddressInfo?


}