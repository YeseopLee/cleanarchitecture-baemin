package com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantFoodEntity
import com.example.cleanarchitecture_baemin.model.restaurant.FoodModel
import com.example.cleanarchitecture_baemin.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantMenuListViewModel(
    private val restaurantId: Long,
    private val foodEntityList: List<RestaurantFoodEntity>
):BaseViewModel() {

    val restaurantFoodListLiveData = MutableLiveData<List<FoodModel>>()

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantFoodListLiveData.value = foodEntityList.map {
            FoodModel(
                id = it.hashCode().toLong(),
                title = it.title,
                description = it.description,
                price = it.price,
                imageUrl = it.imageUrl,
                restaurantId = restaurantId
            )
        }
    }


}