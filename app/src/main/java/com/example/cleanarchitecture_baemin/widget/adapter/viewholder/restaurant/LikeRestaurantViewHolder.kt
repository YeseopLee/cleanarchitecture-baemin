package com.example.cleanarchitecture_baemin.widget.adapter.viewholder.restaurant

import com.example.cleanarchitecture_baemin.R
import com.example.cleanarchitecture_baemin.databinding.ViewholderEmptyBinding
import com.example.cleanarchitecture_baemin.databinding.ViewholderLikeRestaurantBinding
import com.example.cleanarchitecture_baemin.databinding.ViewholderRestaurantBinding
import com.example.cleanarchitecture_baemin.extensions.clear
import com.example.cleanarchitecture_baemin.extensions.load
import com.example.cleanarchitecture_baemin.model.Model
import com.example.cleanarchitecture_baemin.model.restaurant.RestaurantModel
import com.example.cleanarchitecture_baemin.screen.base.BaseViewModel
import com.example.cleanarchitecture_baemin.util.provider.ResourcesProvider
import com.example.cleanarchitecture_baemin.widget.adapter.listener.AdapterListener
import com.example.cleanarchitecture_baemin.widget.adapter.listener.restaurant.RestaurantLikeListListener
import com.example.cleanarchitecture_baemin.widget.adapter.listener.restaurant.RestaurantListListener
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.ModelViewHolder

class LikeRestaurantViewHolder(
    private val binding:ViewholderLikeRestaurantBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
): ModelViewHolder<RestaurantModel>(binding, viewModel, resourcesProvider) {
    override fun reset() = with(binding) {
        restaurantImage.clear()
    }
    override fun bindData(model: RestaurantModel) {
        super.bindData(model)
        with(binding) {
            restaurantImage.load(model.restaurantImageUrl, 24f)
            restaurantTitleText.text = model.restaurantTitle
            gradeText.text = resourcesProvider.getString(R.string.grade_format, model.grade)
            reviewCountText.text = resourcesProvider.getString(R.string.review_count, model.reviewCount)
            val (minTime, maxTime) = model.deliveryTimeRange
            deliveryTimeText.text = resourcesProvider.getString(R.string.delivery_time, minTime, maxTime)

            val (minTip, maxTip) = model.deliveryTipRange
            deliveryTipText.text = resourcesProvider.getString(R.string.delivery_tip, minTip, maxTip)
        }
    }

    override fun bindViews(model: RestaurantModel, adapterListener: AdapterListener) = with(binding) {
        if(adapterListener is RestaurantLikeListListener) {
            root.setOnClickListener {
                adapterListener.onClickItem(model)
            }
            likeImageButton.setOnClickListener {
                adapterListener.onDislikeItem(model)
            }
        }
    }
}