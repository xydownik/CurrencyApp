package com.example.currencyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencylib.domain.model.Currency
import com.example.currencylib.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CurrencyUiState {
    data object Loading : CurrencyUiState()
    data class Success(val data: List<Currency>) : CurrencyUiState()
    data class Error(val message: String) : CurrencyUiState()
    data object Empty : CurrencyUiState()
}

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    private val _base = MutableStateFlow("USD")
    val base: StateFlow<String> = _base.asStateFlow()

    private val _state = MutableStateFlow<CurrencyUiState>(CurrencyUiState.Empty)
    val state: StateFlow<CurrencyUiState> = _state.asStateFlow()

    fun onBaseChanged(newBase: String) {
        _base.value = newBase.uppercase()
    }

    fun loadCurrencies() {
        val currentBase = _base.value
        if (currentBase.isBlank()) {
            _state.value = CurrencyUiState.Error("Введите базовую валюту")
            return
        }

        _state.value = CurrencyUiState.Loading
        viewModelScope.launch {
            try {
                val result = getCurrenciesUseCase(currentBase)
                _state.value = CurrencyUiState.Success(result)
            } catch (e: Exception) {
                _state.value = CurrencyUiState.Error("Ошибка: ${e.localizedMessage}")
            }
        }
    }
}
