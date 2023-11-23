package com.task.currexchangerates.di

import com.task.currexchangerates.data.ExchangeRatesApi
import com.task.currexchangerates.util.DispatcherProvider
import com.task.currexchangerates.view.ExchangeRepository
import com.task.currexchangerates.view.ExchangeRepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.exchangeratesapi.io/"
private const val BASE_URL_TEST = "https://api.apilayer.com/"

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyApi(): ExchangeRatesApi = Retrofit.Builder()
        .baseUrl(BASE_URL_TEST)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ExchangeRatesApi::class.java)

    @Singleton
    @Provides
    fun providePrimeRepository(api: ExchangeRatesApi): ExchangeRepository = ExchangeRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}