package com.example.cleanarchitecture_baemin.di


import com.example.cleanarchitecture_baemin.data.repository.map.DefaultMapRepository
import com.example.cleanarchitecture_baemin.data.repository.map.MapRepository
import com.example.cleanarchitecture_baemin.data.repository.restaurant.DefaultRestaurantRepository
import com.example.cleanarchitecture_baemin.data.repository.restaurant.RestaurantRepository
import com.example.cleanarchitecture_baemin.screen.main.home.HomeViewModel
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantCategory
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantListViewModel
import com.example.cleanarchitecture_baemin.screen.main.my.MyViewModel
import com.example.cleanarchitecture_baemin.util.provider.DefaultResourcesProvider
import com.example.cleanarchitecture_baemin.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { HomeViewModel(get()) }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory: RestaurantCategory) -> RestaurantListViewModel(restaurantCategory, get()) }

    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get()) }
    single<MapRepository> { DefaultMapRepository(get(), get())}

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }

    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }

    single { provideMapApiService(get())}
    single { provideMapRetrofit(get(), get())}
}