package com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail.menu

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantFoodEntity
import com.example.cleanarchitecture_baemin.databinding.FragmentListBinding
import com.example.cleanarchitecture_baemin.model.restaurant.FoodModel
import com.example.cleanarchitecture_baemin.model.restaurant.RestaurantModel
import com.example.cleanarchitecture_baemin.screen.base.BaseFragment
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantListViewModel
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail.RestaurantDetailActivity
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import com.example.cleanarchitecture_baemin.util.provider.ResourcesProvider
import com.example.cleanarchitecture_baemin.widget.adapter.ModelRecyclerAdapter
import com.example.cleanarchitecture_baemin.widget.adapter.listener.AdapterListener
import com.example.cleanarchitecture_baemin.widget.adapter.listener.restaurant.FoodMenuListListener
import com.example.cleanarchitecture_baemin.widget.adapter.listener.restaurant.RestaurantListListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantMenuListFragment: BaseFragment<RestaurantMenuListViewModel, FragmentListBinding>() {

    private val restaurantId by lazy { arguments?.getLong(RESTAURANT_ID_KEY, -1)}
    private val restaurantFoodList by lazy { arguments?.getParcelableArrayList<RestaurantFoodEntity>(
        FOOD_LIST_KEY)}

    override val viewModel by viewModel<RestaurantMenuListViewModel> {
        parametersOf(
            restaurantId,
            restaurantFoodList
        )
    }

    /**
     * 다른 viewmodel에 접근하기
     * **/
    private val restaurantDetailViewModel by sharedViewModel<RestaurantDetailViewModel>()

    override fun getViewBinding(): FragmentListBinding = FragmentListBinding.inflate(layoutInflater)

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<FoodModel, RestaurantMenuListViewModel>(
            listOf(),
            viewModel,
            resourcesProvider,
            adapterListener = object : FoodMenuListListener {
                override fun onClickItem(model: FoodModel) {
                    viewModel.insertMenuInBasket(model)
                }

            }
        )
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }
    override fun observeData() {

        viewModel.restaurantFoodListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.menuBasketLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "메뉴: ${it.title}", Toast.LENGTH_SHORT).show()
            restaurantDetailViewModel.notifyFoodMenuListInBasket(it)
        }

        viewModel.isClearNeedInBasketLiveData.observe(viewLifecycleOwner) { (isClearNeed, afterAction) ->
            if(isClearNeed) {
                restaurantDetailViewModel.notifyClearNeedAlertInBasket(isClearNeed, afterAction)
            }
        }

    }

    companion object {

        const val RESTAURANT_ID_KEY = "restaurantId"
        const val FOOD_LIST_KEY = "foodList"

        fun newInstance(restaurantId: Long, foodList: ArrayList<RestaurantFoodEntity>): RestaurantMenuListFragment {
            val bundle = bundleOf(
                RESTAURANT_ID_KEY to restaurantId,
                FOOD_LIST_KEY to foodList
            )
            return RestaurantMenuListFragment().apply {
                arguments = bundle
            }
        }
    }

}