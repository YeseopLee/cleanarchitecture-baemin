package com.example.cleanarchitecture_baemin.data.repository.user

import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity

interface UserRepository {

    suspend fun getUserLocation(): LocationLatLngEntity?

    suspend fun insertUserLocation(locationLatLngEntity: LocationLatLngEntity)

}