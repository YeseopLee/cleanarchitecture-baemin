package com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail.review

import androidx.core.os.bundleOf
import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantFoodEntity
import com.example.cleanarchitecture_baemin.databinding.FragmentListBinding
import com.example.cleanarchitecture_baemin.screen.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantReviewListFragment: BaseFragment<RestaurantReviewListViewModel, FragmentListBinding>() {

    override val viewModel by viewModel<RestaurantReviewListViewModel>()

    override fun getViewBinding(): FragmentListBinding = FragmentListBinding.inflate(layoutInflater)


    override fun observeData() {
    }

    companion object {

        const val RESTAURANT_ID_KEY = "restaurantId"

        fun newInstance(restaurantId: Long): RestaurantReviewListFragment {
            val bundle = bundleOf(
                RESTAURANT_ID_KEY to restaurantId

            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }
    }

}