package com.example.myapplication.api

import com.example.myapplication.models.Cep
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CepApi {
    @GET("{cep}/json")
    suspend fun fetchCepDetails(@Path("cep") cep: String): Response<Cep>
}
