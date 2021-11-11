package com.example.cleanarchitecture_baemin.widget.adapter.viewholder.review

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.cleanarchitecture_baemin.R
import com.example.cleanarchitecture_baemin.databinding.ViewholderRestaurantBinding
import com.example.cleanarchitecture_baemin.databinding.ViewholderRestaurantReviewBinding
import com.example.cleanarchitecture_baemin.extensions.clear
import com.example.cleanarchitecture_baemin.extensions.load
import com.example.cleanarchitecture_baemin.model.restaurant.RestaurantModel
import com.example.cleanarchitecture_baemin.model.restaurant.review.RestaurantReviewModel
import com.example.cleanarchitecture_baemin.screen.base.BaseViewModel
import com.example.cleanarchitecture_baemin.util.provider.ResourcesProvider
import com.example.cleanarchitecture_baemin.widget.adapter.listener.AdapterListener
import com.example.cleanarchitecture_baemin.widget.adapter.listener.restaurant.RestaurantListListener
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.ModelViewHolder

class RestaurantReviewViewHolder(
    private val binding:ViewholderRestaurantReviewBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
): ModelViewHolder<RestaurantReviewModel>(binding, viewModel, resourcesProvider) {
    override fun reset() = with(binding) {
        reviewThumbnailImage.clear()
        reviewThumbnailImage.isGone = true
    }
    override fun bindData(model: RestaurantReviewModel) {
        super.bindData(model)
        with(binding) {
            if(model.thumbnailImageUri != null) {
                reviewThumbnailImage.isVisible = true
                reviewThumbnailImage.load(model.thumbnailImageUri.toString(), 12f)
            } else {
                reviewThumbnailImage.isGone = true
            }
            reviewTitleText.text = model.title
            reviewText.text = model.description
            ratingBar.rating = model.grade
        }
    }

    override fun bindViews(model: RestaurantReviewModel, adapterListener: AdapterListener) = Unit

}