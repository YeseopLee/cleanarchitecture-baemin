package com.example.cleanarchitecture_baemin.di


import com.example.cleanarchitecture_baemin.util.provider.DefaultResourcesProvider
import com.example.cleanarchitecture_baemin.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication())}

    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }

    single { provideRetrofit(get(), get())}
}