package com.example.cleanarchitecture_baemin.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantEntity
import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantFoodEntity

@Dao
interface FoodMenuBasketDao {

    @Query("SELECT * FROM RestaurantFoodEntity")
    suspend fun getAll(): List<RestaurantFoodEntity>

    @Query("SELECT * FROM RestaurantFoodEntity WHERE id=:foodId AND restaurantId=:restaurantId")
    suspend fun get(restaurantId: Long, foodId: Long): RestaurantFoodEntity?

    @Query("SELECT * FROM RestaurantFoodEntity WHERE restaurantId=:restaurantId")
    suspend fun getAllbyRestaurantId(restaurantId: Long): List<RestaurantFoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurantFoodEntity: RestaurantFoodEntity)

    @Query("DELETE FROM RestaurantFoodEntity WHERE id=:foodId")
    suspend fun delete(foodId: String)

    @Query("DELETE FROM RestaurantFoodEntity")
    suspend fun deleteAll()

}