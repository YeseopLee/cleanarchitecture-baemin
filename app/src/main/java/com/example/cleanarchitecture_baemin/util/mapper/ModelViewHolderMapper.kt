package com.example.cleanarchitecture_baemin.util.mapper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.cleanarchitecture_baemin.databinding.*
import com.example.cleanarchitecture_baemin.model.CellType
import com.example.cleanarchitecture_baemin.model.Model
import com.example.cleanarchitecture_baemin.screen.base.BaseViewModel
import com.example.cleanarchitecture_baemin.util.provider.ResourcesProvider
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.EmptyViewHolder
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.ModelViewHolder
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.food.FoodMenuViewHolder
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.order.OrderMenuViewHolder
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.order.OrderViewHolder
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.restaurant.LikeRestaurantViewHolder
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.restaurant.RestaurantViewHolder
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.review.RestaurantReviewViewHolder

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M: Model> map(
        parent: ViewGroup,
        type: CellType,
        viewModel: BaseViewModel,
        resourcesProvider: ResourcesProvider
    ): ModelViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = when (type) {
            CellType.EMPTY_CELL -> EmptyViewHolder(
                ViewholderEmptyBinding.inflate(inflater, parent , false),
                viewModel,
                resourcesProvider
            )
            CellType.RESTAURANT_CELL -> RestaurantViewHolder(
                ViewholderRestaurantBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.LIKE_RESTAURANT_CELL -> LikeRestaurantViewHolder(
                ViewholderLikeRestaurantBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.FOOD_CELL -> FoodMenuViewHolder(
                ViewholderFoodMenuBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.REVIEW_CELL -> RestaurantReviewViewHolder(
                ViewholderRestaurantReviewBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.ORDER_FOOD_CELL -> OrderMenuViewHolder(
                ViewholderOrderMenuBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.ORDER_CELL -> OrderViewHolder(
                ViewholderOrderBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
        }
        return viewHolder as ModelViewHolder<M>
    }

}