package com.task.currexchangerates.data

import com.task.currexchangerates.BuildConfig
import com.task.currexchangerates.data.models.AllCurrencyResponse
import com.task.currexchangerates.data.models.CurrencyResponse
import com.task.currexchangerates.data.models.IbanResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExchangeRatesApi {

    /*@Headers(
        "apikey: ${BuildConfig.API_KEY}"
    )*/
    @GET("fixer/convert")
    suspend fun getRates(
        @Query("to") to: String, @Query("from") from: String, @Query("amount") amount: String
    ): Response<CurrencyResponse>
    /*@Headers(
        "apikey: ${BuildConfig.API_KEY}"
    )*/
    @GET("fixer/latest")
    suspend fun getAllRates(
        @Query("symbols") to: String = "",  @Query("base") base: String
    ): Response<AllCurrencyResponse>

    /*@Headers(
        "apikey: ${BuildConfig.API_KEY}"
    )*/
    @GET("bank_data/iban_validate")
    suspend fun validateIban(
        @Query("iban_number") iban_number: String
    ): Response<IbanResponse>
}