package com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail

import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantEntity

sealed class RestaurantDetailState {

    object Uninitialized: RestaurantDetailState()

    object Loading: RestaurantDetailState()

    data class Success(
        val restaurantEntity: RestaurantEntity,
        val isLiked: Boolean? = null
    ): RestaurantDetailState()

}