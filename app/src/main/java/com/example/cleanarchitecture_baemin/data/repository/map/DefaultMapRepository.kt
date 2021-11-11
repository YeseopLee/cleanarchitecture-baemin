package com.example.cleanarchitecture_baemin.data.repository.map

import com.example.cleanarchitecture_baemin.data.response.address.AddressInfo
import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity
import com.example.cleanarchitecture_baemin.data.network.MapApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultMapRepository(
    private val mapApiService: MapApiService,
    private val ioDispatcher: CoroutineDispatcher
): MapRepository {

    override suspend fun getReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    ): AddressInfo? = withContext(ioDispatcher) {
        val response = mapApiService.getReverseGeoCode(
            lat = locationLatLngEntity.latitude,
            lon = locationLatLngEntity.longitude
        )
        if (response.isSuccessful) {
            response?.body()?.addressInfo
        } else {
            null
        }
    }
}