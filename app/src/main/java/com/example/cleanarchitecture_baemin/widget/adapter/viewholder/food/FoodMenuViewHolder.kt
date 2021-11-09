package com.example.cleanarchitecture_baemin.widget.adapter.viewholder.food


import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.cleanarchitecture_baemin.R
import com.example.cleanarchitecture_baemin.databinding.ViewholderFoodMenuBinding
import com.example.cleanarchitecture_baemin.extensions.clear
import com.example.cleanarchitecture_baemin.extensions.load
import com.example.cleanarchitecture_baemin.model.restaurant.FoodModel
import com.example.cleanarchitecture_baemin.screen.base.BaseViewModel
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import com.example.cleanarchitecture_baemin.util.provider.ResourcesProvider
import com.example.cleanarchitecture_baemin.widget.adapter.listener.AdapterListener
import com.example.cleanarchitecture_baemin.widget.adapter.listener.restaurant.FoodMenuListListener
import com.example.cleanarchitecture_baemin.widget.adapter.viewholder.ModelViewHolder

class FoodMenuViewHolder(
    private val binding: ViewholderFoodMenuBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<FoodModel>(binding, viewModel, resourcesProvider){

    override fun reset() = with(binding) {
        foodImage.clear()
    }

    override fun bindViews(model: FoodModel, adapterListener: AdapterListener) {
        if(adapterListener is FoodMenuListListener) {
            binding.root.setOnClickListener {
                adapterListener.onClickItem(model)
            }
        }
    }

    override fun bindData(model: FoodModel) {
        super.bindData(model)
        with(binding) {
            foodImage.load(model.imageUrl, 24f, CenterCrop())
            foodTitleText.text = model.title
            foodDescriptionText.text = model.description
            priceText.text = resourcesProvider.getString(R.string.price, model.price)
        }
    }


}