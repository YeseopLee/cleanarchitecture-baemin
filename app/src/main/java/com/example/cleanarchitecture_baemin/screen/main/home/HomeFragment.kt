package com.example.cleanarchitecture_baemin.screen.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.cleanarchitecture_baemin.R
import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity
import com.example.cleanarchitecture_baemin.databinding.FragmentHomeBinding
import com.example.cleanarchitecture_baemin.screen.base.BaseFragment
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantCategory
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantListFragment
import com.example.cleanarchitecture_baemin.widget.adapter.RestaurantListFragmentPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {


    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    private lateinit var locationManager: LocationManager

    private lateinit var myLocationListener: MyLocationListener

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



    private fun initViewPager(locationLatLng: LocationLatLngEntity) = with(binding) {
        val restaurantCategories = RestaurantCategory.values()

        if(::viewPagerAdapter.isInitialized.not()){
            val restaurantListFragmentList =restaurantCategories.map{
                RestaurantListFragment.newInstance(it)
            }

            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList
            )
            viewPager.adapter = viewPagerAdapter
        }
        viewPager.offscreenPageLimit = restaurantCategories.size
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(restaurantCategories[position].categoryNameId)
        }.attach()
    }

    override fun observeData() = viewModel.homeStateLiveData.observe(viewLifecycleOwner) {
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