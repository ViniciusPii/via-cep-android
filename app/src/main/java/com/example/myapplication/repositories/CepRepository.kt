package com.example.myapplication.repositories

import com.example.myapplication.models.Address
import com.example.myapplication.models.State

interface CepRepository {
    suspend fun fetchCepDetails(cep: String): State<Address>
}