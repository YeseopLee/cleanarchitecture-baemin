package com.example.cleanarchitecture_baemin.di


import com.example.cleanarchitecture_baemin.screen.main.MainViewModel
import com.example.cleanarchitecture_baemin.screen.main.home.HomeViewModel
import com.example.cleanarchitecture_baemin.screen.main.my.MyViewModel
import com.example.cleanarchitecture_baemin.util.provider.DefaultResourcesProvider
import com.example.cleanarchitecture_baemin.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { HomeViewModel() }
    viewModel { MyViewModel() }
    viewModel { MainViewModel()}

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication())}

    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }

    single { provideRetrofit(get(), get())}
}