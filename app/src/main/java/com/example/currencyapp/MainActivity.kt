package com.example.currencyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.currencyapp.ui.CurrencyScreen
import com.example.currencyapp.ui.theme.CurrencyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyAppTheme {
                CurrencyScreen()
            }
        }
    }
}
