package com.task.currexchangerates.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.currexchangerates.util.DispatcherProvider
import com.task.currexchangerates.util.Resource
import com.task.currexchangerates.view.ExchangeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

class ExchangeViewModel @ViewModelInject constructor(
    private val repository: ExchangeRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    sealed class CurrencyEvent {
        class Success<T>(val result: T) : CurrencyEvent()
        class Failure(val errorText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _ibanValidation = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val ibanValidation: StateFlow<CurrencyEvent> = _ibanValidation

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    private val _conversion_all = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion_all: StateFlow<CurrencyEvent> = _conversion_all

    fun getAllCurrencyRates(
        code: String
    ) {
        viewModelScope.launch(dispatchers.io) {
            _conversion_all.value = CurrencyEvent.Loading
            when (val ratesResponse = repository.getAllRates(code)) {
                is Resource.Error -> _conversion_all.value =
                    CurrencyEvent.Failure(ratesResponse.message!!)

                is Resource.Success -> {
                    val rates = ratesResponse.data?.rates
                    if (rates == null) {
                        _conversion_all.value = CurrencyEvent.Failure("Unexpected error")
                    } else {
                        _conversion_all.value = CurrencyEvent.Success(
                            rates
                        )
                    }
                }
            }
        }
    }

    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }
        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when (val ratesResponse = repository.getRates(toCurrency, fromCurrency, amountStr)) {
                is Resource.Error -> _conversion.value =
                    CurrencyEvent.Failure(ratesResponse.message!!)

                is Resource.Success -> {
                    val rates = ratesResponse.data?.info?.rate
                    if (rates == null) {
                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    } else {
                        val convertedCurrency = round(fromAmount * rates * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }
    }

    fun ibanValidate(
        ibanNumber: String,
    ) {
        if (ibanNumber.isEmpty()) {
            _ibanValidation.value = CurrencyEvent.Failure("Not a valid IBAN Number")
            return
        }


        viewModelScope.launch(dispatchers.io) {
            _ibanValidation.value = CurrencyEvent.Loading
            when (val response = repository.validateIban(ibanNumber)) {
                is Resource.Error -> _ibanValidation.value =
                    CurrencyEvent.Failure(response.message!!)

                is Resource.Success -> {
                    val message = response.data?.message
                    if (message == null) {
                        _ibanValidation.value = CurrencyEvent.Failure("Unexpected error")
                    } else {
                        _ibanValidation.value = CurrencyEvent.Success(
                            "$message"
                        )
                    }
                }
            }
        }
    }
}
