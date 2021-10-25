package com.example.cleanarchitecture_baemin.screen.main.home.restaurant

import androidx.annotation.StringRes
import com.example.cleanarchitecture_baemin.R

enum class RestaurantCategory (
    @StringRes val categoryNameId: Int,
    @StringRes val categoryTypeId: Int
    ) {

    ALL(R.string.all, R.string.all_type)
}