package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.models.converters.AddressConverter
import com.example.myapplication.models.converters.AddressConverterImpl
import com.example.myapplication.repositories.CepRepository
import com.example.myapplication.repositories.CepRepositoryImpl
import com.example.myapplication.rest.RetrofitService
import com.example.myapplication.ui.viewmodels.CepViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class CepApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }

    private val appModule = module {
        single { RetrofitService.providerCepApi() }
        single<CepRepository> { CepRepositoryImpl(cepApi = get(), addressConverter = get()) }

        factory<AddressConverter> { AddressConverterImpl() }

        viewModel { CepViewModel(cepRepository = get()) }
    }
}
