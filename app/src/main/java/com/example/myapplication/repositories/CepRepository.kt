package com.example.myapplication.repositories

import com.example.myapplication.models.Address
import com.example.myapplication.models.Resource

interface CepRepository {
    suspend fun fetchCepDetails(cep: String): Resource<Address>
}