package com.example.currencyapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.currencylib.domain.model.Currency

@Composable
fun CurrencyItem(currency: Currency) {
    val backgroundColor = if (currency.change24h >= 0) Color(0xFFD0F0C0) else Color(0xFFFFC1C1)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = currency.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Rate: %.2f".format(currency.rate), style = MaterialTheme.typography.bodyMedium)
            Text(text = "Change: %.2f".format(currency.change24h), style = MaterialTheme.typography.bodySmall)
        }
    }
}
