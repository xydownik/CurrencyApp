package com.example.currencyapp.ui
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyapp.viewmodel.CurrencyUiState
import com.example.currencyapp.viewmodel.CurrencyViewModel

@Composable
fun CurrencyScreen(viewModel: CurrencyViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val base by viewModel.base.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        // Поле ввода
        OutlinedTextField(
            value = base,
            onValueChange = viewModel::onBaseChanged,
            label = { Text("Базовая валюта (например, USD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Кнопка загрузки
        Button(
            onClick = { viewModel.loadCurrencies() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Загрузить")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Состояния
        when (val currentState = state) {
            is CurrencyUiState.Empty -> {
                Text("Введите базовую валюту и нажмите 'Загрузить'")
            }

            is CurrencyUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CurrencyUiState.Success -> {
                LazyColumn {
                    items(currentState.data) { currency ->
                        CurrencyItem(currency = currency)
                    }
                }
            }

            is CurrencyUiState.Error -> {
                Text(text = currentState.message, color = Color.Red)
            }
        }
    }
}
