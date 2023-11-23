package com.task.currexchangerates.di

import com.task.currexchangerates.BuildConfig
import com.task.currexchangerates.data.ExchangeRatesApi
import com.task.currexchangerates.util.DispatcherProvider
import com.task.currexchangerates.util.SSLPinning
import com.task.currexchangerates.view.ExchangeRepository
import com.task.currexchangerates.view.ExchangeRepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getPinnedOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .certificatePinner(SSLPinning.getPinnedCertificate())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    /*
    *  OKhttp client Interceptor header added
    * */
    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request()
                val newRquest = request.newBuilder().header("apikey", BuildConfig.API_KEY)
                chain.proceed(newRquest.build())
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyApi(): ExchangeRatesApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        //.client(getPinnedOkHttpClient())
        .client(provideOkhttpClient())
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