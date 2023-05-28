package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.api.CepApi
import com.example.myapplication.repositories.CepRepositoryImpl
import com.example.myapplication.ui.viewmodels.CepViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CepApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }

    private val appModule = module {
        single {
            Retrofit.Builder().baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(CepApi::class.java)
        }

        single { CepRepositoryImpl(cepApi = get()) }
        viewModel { CepViewModel(cepRepository = get()) }
    }
}
