package com.example.cleanarchitecture_baemin.data.repository.restaurant.review

import com.example.cleanarchitecture_baemin.data.entitiy.ReviewEntity

interface RestaurantReviewRepository {

    suspend fun getReviews(restaurantTitle: String): DefaultRestaurantReviewRepository.Result

}