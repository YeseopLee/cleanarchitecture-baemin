package com.example.cleanarchitecture_baemin.widget.adapter.listener.order

import com.example.cleanarchitecture_baemin.model.restaurant.FoodModel
import com.example.cleanarchitecture_baemin.widget.adapter.listener.AdapterListener

interface OrderMenuListListener: AdapterListener {

    fun onRemoveItem(model: FoodModel)
}