package com.example.cleanarchitecture_baemin.data.repository.order

import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantFoodEntity

interface OrderRepository {

    suspend fun orderMenu(
        userId: String,
        restaurantId: Long,
        foodMenuList: List<RestaurantFoodEntity>
    ) : DefaultOrderRepository.Result
}