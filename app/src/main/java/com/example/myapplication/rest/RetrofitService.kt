package com.example.myapplication.rest

import com.example.myapplication.api.CepApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        private const val BASE_URL = "https://viacep.com.br/ws/"

        fun providerCepApi(): CepApi {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CepApi::class.java)
        }
    }
}