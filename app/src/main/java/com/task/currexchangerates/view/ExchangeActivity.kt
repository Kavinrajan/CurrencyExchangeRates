package com.task.currexchangerates.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.task.currexchangerates.databinding.ActivityExchangeBinding
import com.task.currexchangerates.view.viewmodel.ExchangeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ExchangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExchangeBinding

    private val viewModel: ExchangeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        *  Currency Converter
        * */

        binding.btnConvert.setOnClickListener {
            viewModel.convert(
                binding.etFrom.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString(),
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when(event) {
                    is ExchangeViewModel.CurrencyEvent.Success<*> -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.GREEN)
                        binding.tvResult.text = event.result.toString()
                    }
                    is ExchangeViewModel.CurrencyEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.text = event.errorText
                    }
                    is ExchangeViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }

        /*
        *  IBAN Validation
        * */

        binding.ibanValidate.setOnClickListener {
            viewModel.ibanValidate(
                binding.ibanValidateTxt.text.toString()
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.ibanValidation.collect { event ->
                when(event) {
                    is ExchangeViewModel.CurrencyEvent.Success<*> -> {
                        binding.ibanProgressBar.isVisible = false
                        binding.ibanResult.setTextColor(Color.GREEN)
                        binding.ibanResult.text = event.result.toString()
                    }
                    is ExchangeViewModel.CurrencyEvent.Failure -> {
                        binding.ibanProgressBar.isVisible = false
                        binding.ibanResult.setTextColor(Color.RED)
                        binding.ibanResult.text = event.errorText
                    }
                    is ExchangeViewModel.CurrencyEvent.Loading -> {
                        binding.ibanProgressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }
}