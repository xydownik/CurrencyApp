package com.example.currencyapp.di

import android.content.Context
import com.example.currencyapp.BuildConfig.API_KEY
import com.example.currencylib.data.api.ExchangeApi
import com.example.currencylib.data.local.CurrencyRateStorage
import com.example.currencylib.domain.repository.CurrencyRepository
import com.example.currencylib.domain.usecase.ConvertCurrencyUseCase
import com.example.currencylib.domain.usecase.GetCurrenciesUseCase
import com.example.currencylib.data.repository.CurrencyRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyModule {
    @Provides
    @Singleton
    fun provideRateStorage(@ApplicationContext context: Context): CurrencyRateStorage {
        return CurrencyRateStorage(context)
    }

    @Provides
    @Named("API_KEY")
    fun provideApiKey(): String = API_KEY

    @Provides
    @Singleton
    fun provideExchangeApi(): ExchangeApi {
        return Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        api: ExchangeApi,
        @Named("API_KEY") apiKey: String,
        storage: CurrencyRateStorage
    ): CurrencyRepository = CurrencyRepositoryImpl(api, storage, apiKey)

    @Provides
    fun provideGetCurrenciesUseCase(
        repository: CurrencyRepository
    ): GetCurrenciesUseCase = GetCurrenciesUseCase(repository)

    @Provides
    fun provideConvertCurrencyUseCase(
        repository: CurrencyRepository
    ): ConvertCurrencyUseCase = ConvertCurrencyUseCase(repository)
}
