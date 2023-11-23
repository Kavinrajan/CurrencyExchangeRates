package com.task.currexchangerates.view

import com.task.currexchangerates.data.models.AllCurrencyResponse
import com.task.currexchangerates.data.models.CurrencyResponse
import com.task.currexchangerates.data.models.IbanResponse
import com.task.currexchangerates.util.Resource


interface ExchangeRepository {

    suspend fun getRates(to: String, from: String, amount: String): Resource<CurrencyResponse>

    suspend fun getAllRates(baseCode: String): Resource<AllCurrencyResponse>

    suspend fun validateIban(ibanValue: String): Resource<IbanResponse>

}