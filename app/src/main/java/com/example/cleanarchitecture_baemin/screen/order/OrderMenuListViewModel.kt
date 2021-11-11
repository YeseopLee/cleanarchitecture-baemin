package com.example.cleanarchitecture_baemin.screen.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture_baemin.R
import com.example.cleanarchitecture_baemin.data.repository.order.DefaultOrderRepository
import com.example.cleanarchitecture_baemin.data.repository.order.OrderRepository
import com.example.cleanarchitecture_baemin.data.repository.restaurant.food.RestaurantFoodRepository
import com.example.cleanarchitecture_baemin.model.CellType
import com.example.cleanarchitecture_baemin.model.restaurant.FoodModel
import com.example.cleanarchitecture_baemin.screen.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class OrderMenuListViewModel(
    private val restaurantFoodRepository: RestaurantFoodRepository,
    private val orderRepository: OrderRepository
):BaseViewModel() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    val orderMenuStateLiveData = MutableLiveData<OrderMenuState>(OrderMenuState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        orderMenuStateLiveData.value = OrderMenuState.Loading
        val foodMenuList = restaurantFoodRepository.getAllFoodMenuListInBasket()
        orderMenuStateLiveData.value = OrderMenuState.Success(
            foodMenuList.map {
                FoodModel(
                    id = it.hashCode().toLong(),
                    type = CellType.ORDER_FOOD_CELL,
                    title = it.title,
                    description = it.description,
                    price = it.price,
                    imageUrl = it.imageUrl,
                    restaurantId = it.restaurantId,
                    foodId = it.id,
                    restaurantTitle = it.restaurantTitle
                )
            }
        )
    }

    fun orderMenu() = viewModelScope.launch {
        val foodMenuList = restaurantFoodRepository.getAllFoodMenuListInBasket()
        if (foodMenuList.isNotEmpty()) {
            val restaurantId = foodMenuList.first().restaurantId
            val restaurantTitle = foodMenuList.first().restaurantTitle
            firebaseAuth.currentUser?.let { user ->
                when (val data = orderRepository.orderMenu(user.uid, restaurantId, foodMenuList, restaurantTitle)) {
                    is DefaultOrderRepository.Result.Success<*> -> {
                        restaurantFoodRepository.clearFoodMenuListInBasket()
                        orderMenuStateLiveData.value = OrderMenuState.Order
                    }
                    is DefaultOrderRepository.Result.Error -> {
                        orderMenuStateLiveData.value = OrderMenuState.Error(
                            R.string.request_error, data.e
                        )
                    }
                }
            } ?: kotlin.run {
                orderMenuStateLiveData.value = OrderMenuState.Error(
                    R.string.user_id_not_found, IllegalStateException()
                )
            }
        }
    }

    fun clearOrderMenu() = viewModelScope.launch {
        restaurantFoodRepository.clearFoodMenuListInBasket()
        fetchData()
    }

    fun removeOrderMenu(model: FoodModel) = viewModelScope.launch {
        restaurantFoodRepository.removeFoodMenuListInBasket(model.foodId)
        fetchData()
    }
}