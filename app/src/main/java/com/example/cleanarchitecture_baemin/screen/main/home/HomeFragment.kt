package com.example.cleanarchitecture_baemin.screen.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.cleanarchitecture_baemin.R
import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity
import com.example.cleanarchitecture_baemin.data.entitiy.MapSearchInfoEntity
import com.example.cleanarchitecture_baemin.databinding.FragmentHomeBinding
import com.example.cleanarchitecture_baemin.screen.base.BaseFragment
import com.example.cleanarchitecture_baemin.screen.main.MainActivity
import com.example.cleanarchitecture_baemin.screen.main.MainTabMenu
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantCategory
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantListFragment
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantOrder
import com.example.cleanarchitecture_baemin.screen.mylocation.MyLocationActivity
import com.example.cleanarchitecture_baemin.screen.order.OrderMenuListActivity
import com.example.cleanarchitecture_baemin.util.event.MenuChangeEventBus
import com.example.cleanarchitecture_baemin.widget.adapter.RestaurantListFragmentPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {


    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val menuChangeEventBus by inject<MenuChangeEventBus>()

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    private lateinit var locationManager: LocationManager

    private lateinit var myLocationListener: MyLocationListener

    // 동작확인해보기
    private val changeLocationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getParcelableExtra<MapSearchInfoEntity>(HomeViewModel.MY_LOCATION_KEY)
                ?.let { myLocationInfo -> viewModel.loadReverseGeoInformation(myLocationInfo.locationLatLng)
                }
        }
    }

    // permission 체크 런쳐
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissons ->
            val responsePermissions = permissons.entries.filter {
                (it.key == Manifest.permission.ACCESS_FINE_LOCATION && it.value)
                        || (it.key == Manifest.permission.ACCESS_COARSE_LOCATION && it.value)
            }
            if(responsePermissions.filter { it.value == true }.size == locationPermission.size ) {
                setMyLocationListener()
            } else {
                with(binding.locationText) {
                    setText("위치권한을 설정해주세요")
                    setOnClickListener{
                        getMyLocation()
                    }
                }
                Toast.makeText(requireContext(), "권한을 받지 못했습니다", Toast.LENGTH_SHORT).show()
            }
        }


    override fun initViews() = with(binding) {
        locationText.setOnClickListener {
            viewModel.getMapSearchInfo()?.let{ mapInfo ->
                changeLocationLauncher.launch(
                    MyLocationActivity.newIntent(
                        requireContext(), mapInfo
                    )
                )
            }
        }
        
        orderChipGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.chipDefault -> {
                    chipInit.isGone = true
                    changeRestaurantOrder(RestaurantOrder.DEFAULT)
                }
                R.id.chipInit -> {
                    chipDefault.isChecked = true

                }
                R.id.chipLowDeliveryTip -> {
                    chipInit.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.LOW_DELIVERY_TIP)
                }
                R.id.chipFastDelivery -> {
                    chipInit.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.FAST_DELIVERY)
                }
                R.id.chipTopRate -> {
                    chipInit.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.TOP_RATE)
                }
            }
        }
    }

    private fun changeRestaurantOrder(order: RestaurantOrder) {
        viewPagerAdapter.fragmentList.forEach {
            it.viewModel.setRestaurantOrder(order)
        }
    }


    private fun initViewPager(locationLatLng: LocationLatLngEntity) = with(binding) {
        val restaurantCategories = RestaurantCategory.values()

        if(::viewPagerAdapter.isInitialized.not()){
            val restaurantListFragmentList =restaurantCategories.map{
                RestaurantListFragment.newInstance(it, locationLatLng)
            }

            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList,
                locationLatLng
            )
            viewPager.adapter = viewPagerAdapter

            viewPager.offscreenPageLimit = restaurantCategories.size
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.setText(restaurantCategories[position].categoryNameId)
            }.attach()
        }
        if (locationLatLng != viewPagerAdapter.locationLatLngEntity) {
            viewPagerAdapter.locationLatLngEntity = locationLatLng
            viewPagerAdapter.fragmentList.forEach {
                it.viewModel.setLocationLatLng(locationLatLng)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkMyBasket()
    }

    override fun observeData() {
        viewModel.homeStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is HomeState.Uninitialized -> {
                    getMyLocation()
                }
                is HomeState.Loading -> {
                    binding.locationLoading.isVisible = true
                    binding.locationText.text = getString(R.string.loading)
                }
                is HomeState.Success -> {
                    binding.locationLoading.isGone = true
                    binding.locationText.text = it.mapSearchInfo.fullAddress
                    binding.tabLayout.isVisible = true
                    binding.filterScrollView.isVisible = true
                    binding.viewPager.isVisible = true
                    initViewPager(it.mapSearchInfo.locationLatLng)
                    if(it.isLocationSame.not()) {
                        Toast.makeText(requireContext(), R.string.please_set_your_location, Toast.LENGTH_SHORT).show()
                    }
                }
                is HomeState.Error -> {
                    binding.locationLoading.isGone = true
                    binding.locationText.text = getString(R.string.can_not_load_address_info)
                    binding.locationText.setOnClickListener {
                        getMyLocation()
                    }
                    Toast.makeText(requireContext(),it.messageId,Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.foodMenuInBasketLiveData.observe(this) {
            if(it.isNotEmpty()) {
                binding.basketButtonContainer.isVisible = true
                binding.baseketCountTextView.text = getString(R.string.basket_count, it.size)
                binding.basketButton.setOnClickListener {
                    if(firebaseAuth.currentUser == null) {
                        alertLoginNeed {
                            lifecycleScope.launch {
                                menuChangeEventBus.changeMenu(MainTabMenu.MY)
                            }
//                            (requireActivity() as MainActivity).goToTab(MainTabMenu.MY)
                        }
                    } else {
                        startActivity(
                            OrderMenuListActivity.newIntent(requireContext())
                        )
                    }
                }
            } else {
                binding.basketButtonContainer.isGone = true
                binding.basketButton.setOnClickListener(null)
            }
        }
    }

    private fun alertLoginNeed(afterAction: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("로그인이 필요합니다.")
            .setMessage("주문하려면 로그인이 필요합니다. My탭으로 이동하시겠습니까?")
            .setPositiveButton("이동") { dialog, _ ->
                afterAction()
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun getMyLocation() {
        if (::locationManager.isInitialized.not()) {
            locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        }
        val isGpsUnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsUnabled) {
            locationPermissionLauncher.launch(locationPermission)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMyLocationListener() {
        val minTime = 1500L //1.5초
        val minDistance = 100f //100m
        if (::myLocationListener.isInitialized.not()) {
            myLocationListener = MyLocationListener()
        }
        with(locationManager) {
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime, minDistance, myLocationListener
            )
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime, minDistance, myLocationListener
            )
        }
    }

    companion object {

        val locationPermission = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        fun newInstance() = HomeFragment()

        const val TAG = "HomeFragment"
    }

    private fun removeLocationListener() {
        if(::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }

    inner class MyLocationListener: LocationListener {
        override fun onLocationChanged(location: Location) {
            viewModel.loadReverseGeoInformation(
                LocationLatLngEntity(
                    location.latitude,
                    location.longitude
                )
            )
            removeLocationListener()
        }
    }
}