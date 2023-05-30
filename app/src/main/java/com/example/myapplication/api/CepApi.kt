package com.example.myapplication.api

import com.example.myapplication.models.response.AddressResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CepApi {
    @GET("{cep}/json")
    suspend fun fetchAddress(@Path("cep") cep: String): Response<AddressResponse>
}
