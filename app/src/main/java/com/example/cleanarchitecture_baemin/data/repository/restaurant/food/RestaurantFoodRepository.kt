package com.example.cleanarchitecture_baemin.data.repository.restaurant.food

import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantFoodEntity

interface RestaurantFoodRepository {

    suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity>

}