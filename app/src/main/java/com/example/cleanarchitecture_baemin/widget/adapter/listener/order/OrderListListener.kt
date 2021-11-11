package com.example.cleanarchitecture_baemin.widget.adapter.listener.order

import com.example.cleanarchitecture_baemin.widget.adapter.listener.AdapterListener

interface OrderListListener: AdapterListener {

    fun writeRestaurantReview(orderId: String, restaurantTitle: String)
}