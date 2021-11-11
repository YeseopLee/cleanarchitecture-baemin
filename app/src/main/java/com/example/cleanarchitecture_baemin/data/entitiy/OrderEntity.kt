package com.example.cleanarchitecture_baemin.data.entitiy

data class OrderEntity(
    val id: String,
    var userId: String,
    val restaurantId: Long,
    val foodMenuList: List<RestaurantFoodEntity>
)