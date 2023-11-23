package com.task.currexchangerates.view

import com.task.currexchangerates.data.ExchangeRatesApi
import com.task.currexchangerates.data.models.AllCurrencyResponse
import com.task.currexchangerates.data.models.CurrencyResponse
import com.task.currexchangerates.data.models.IbanResponse
import com.task.currexchangerates.util.Resource
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(
    private val api: ExchangeRatesApi
) : ExchangeRepository {

    override suspend fun getRates(to: String, from: String, amount: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(to, from, amount)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }

    override suspend fun getAllRates(baseCode: String): Resource<AllCurrencyResponse> {
        return try {
            val response = api.getAllRates("", baseCode)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }

    override suspend fun validateIban(ibanNumber: String): Resource<IbanResponse> {
        return try {
            val response = api.validateIban(ibanNumber)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}