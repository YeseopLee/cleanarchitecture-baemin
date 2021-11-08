package com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail

import androidx.annotation.StringRes
import com.example.cleanarchitecture_baemin.R

enum class RestaurantCategoryDetail(
    @StringRes val categoryNameId: Int
) {

    MENU(R.string.menu), REVIEW(R.string.review)
}