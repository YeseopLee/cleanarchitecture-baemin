package com.example.cleanarchitecture_baemin.widget.adapter.listener.restaurant

import com.example.cleanarchitecture_baemin.model.restaurant.RestaurantModel

interface RestaurantLikeListListener: RestaurantListListener {

    fun onDislikeItem(model: RestaurantModel)

}