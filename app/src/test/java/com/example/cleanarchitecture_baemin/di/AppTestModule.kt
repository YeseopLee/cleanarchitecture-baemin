package com.example.cleanarchitecture_baemin.di

import com.example.cleanarchitecture_baemin.data.TestRestaurantRepository
import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity
import com.example.cleanarchitecture_baemin.data.repository.restaurant.RestaurantRepository
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantCategory
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    viewModel { (restaurantCategory: RestaurantCategory, locationLatLng: LocationLatLngEntity) ->
        RestaurantListViewModel(restaurantCategory,locationLatLng, get()) }


    single<RestaurantRepository> { TestRestaurantRepository() }

}