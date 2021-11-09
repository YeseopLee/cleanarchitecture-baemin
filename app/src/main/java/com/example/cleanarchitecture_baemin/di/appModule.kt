package com.example.cleanarchitecture_baemin.di


import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity
import com.example.cleanarchitecture_baemin.data.entitiy.MapSearchInfoEntity
import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantEntity
import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantFoodEntity
import com.example.cleanarchitecture_baemin.data.preference.AppPreferenceManager
import com.example.cleanarchitecture_baemin.data.repository.map.DefaultMapRepository
import com.example.cleanarchitecture_baemin.data.repository.map.MapRepository
import com.example.cleanarchitecture_baemin.data.repository.restaurant.DefaultRestaurantRepository
import com.example.cleanarchitecture_baemin.data.repository.restaurant.RestaurantRepository
import com.example.cleanarchitecture_baemin.data.repository.restaurant.food.DefaultRestaurantFoodRepository
import com.example.cleanarchitecture_baemin.data.repository.restaurant.food.RestaurantFoodRepository
import com.example.cleanarchitecture_baemin.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import com.example.cleanarchitecture_baemin.data.repository.restaurant.review.RestaurantReviewRepository
import com.example.cleanarchitecture_baemin.data.repository.user.DefaultUserRepository
import com.example.cleanarchitecture_baemin.data.repository.user.UserRepository
import com.example.cleanarchitecture_baemin.screen.main.home.HomeViewModel
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantCategory
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.RestaurantListViewModel
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import com.example.cleanarchitecture_baemin.screen.main.home.restaurant.detail.review.RestaurantReviewListViewModel
import com.example.cleanarchitecture_baemin.screen.main.like.RestaurantLikeListViewModel
import com.example.cleanarchitecture_baemin.screen.main.my.MyViewModel
import com.example.cleanarchitecture_baemin.screen.mylocation.MyLocationViewModel
import com.example.cleanarchitecture_baemin.util.provider.DefaultResourcesProvider
import com.example.cleanarchitecture_baemin.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { MyViewModel(get()) }
    viewModel { (restaurantCategory: RestaurantCategory, locationLatLng: LocationLatLngEntity) -> RestaurantListViewModel(restaurantCategory,locationLatLng, get()) }
    viewModel { (mapSearchInfoEntity: MapSearchInfoEntity) -> MyLocationViewModel(mapSearchInfoEntity, get(), get())}
    viewModel { (restaurantEntity: RestaurantEntity) -> RestaurantDetailViewModel(restaurantEntity, get(), get()) }
    viewModel { (restaurantId: Long, restaurantFoodList: List<RestaurantFoodEntity>) -> RestaurantMenuListViewModel(restaurantId,restaurantFoodList, get()) }
    viewModel { (restaurantTitle: String) -> RestaurantReviewListViewModel(restaurantTitle, get()) }
    viewModel { RestaurantLikeListViewModel(get()) }

    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get(), get()) }
    single<MapRepository> { DefaultMapRepository(get(), get())}
    single<UserRepository> { DefaultUserRepository(get(), get(), get())}
    single<RestaurantFoodRepository> { DefaultRestaurantFoodRepository(get(),get(), get())}
    single<RestaurantReviewRepository> { DefaultRestaurantReviewRepository(get())}

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }
    single {AppPreferenceManager(androidApplication())}

    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }

    /* koin에서 qualifier를 사용하여 retrofit 객체를 분류하여 생성 */
    single(named("map")) { provideMapRetrofit(get(), get())}
    single(named("food")) { provideFoodRetrofit(get(), get())}

    single { provideMapApiService(get(qualifier = named("map")))}
    single { provideFoodApiService(get(qualifier = named("food")))}

    single { provideDB(androidApplication()) }
    single { provideLocationDao(get())}
    single { provideRestaurantDao(get())}
    single { provideFoodMenuBasketDao(get()) }
}