package com.example.cleanarchitecture_baemin.data.repository.restaurant.review

import com.example.cleanarchitecture_baemin.data.entitiy.ReviewEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultRestaurantReviewRepository(
    private val ioDispatcher: CoroutineDispatcher
): RestaurantReviewRepository {

    override suspend fun getReviews(restaurantTitle: String): List<ReviewEntity> = withContext(ioDispatcher) {
        return@withContext (0..10).map {
            ReviewEntity(
                id=0,
                title="제목 $it",
                description = "내용 $it",
                grade = (1 until 5).random()
            )
        }
    }
}