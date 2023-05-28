package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.models.converters.AddressConverter
import com.example.myapplication.models.converters.AddressConverterImpl
import com.example.myapplication.repositories.AddressRepository
import com.example.myapplication.repositories.AddressRepositoryImpl
import com.example.myapplication.rest.RetrofitService
import com.example.myapplication.ui.viewmodels.AddressViewModel
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
        single<AddressRepository> { AddressRepositoryImpl(cepApi = get(), addressConverter = get()) }

        factory<AddressConverter> { AddressConverterImpl() }

        viewModel { AddressViewModel(addressRepository = get()) }
    }
}
